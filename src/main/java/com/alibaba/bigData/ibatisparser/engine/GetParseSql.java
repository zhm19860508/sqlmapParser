package com.alibaba.bigData.ibatisparser.engine;

import com.alibaba.bigData.ibatisparser.configuration.Commons;
import com.alibaba.bigData.ibatisparser.dynamicnode.CdataNode;
import com.alibaba.bigData.ibatisparser.dynamicnode.INodeElement;
import com.alibaba.bigData.ibatisparser.dynamicnode.TextNode;
import com.alibaba.bigData.ibatisparser.objects.SqlMapNodes;
import com.alibaba.bigData.ibatisparser.sqlmeta.ParserMetaResult;
import com.alibaba.bigData.ibatisparser.utils.DruidUtils;
import com.alibaba.bigData.ibatisparser.utils.NoOpEntityResolver;
import com.alibaba.bigData.ibatisparser.utils.SqlFormatUtils;
import com.alibaba.bigData.ibatisparser.utils.ZHMSQLUtil;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.StringUtils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultCDATA;
import org.dom4j.tree.DefaultComment;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: xiaoming.wm Date: 14-2-16 Time: 下午5:38 根据Xml获取组合的sql结果
 */
public class GetParseSql {

    // 原生sqlxml文本
    String                      rawSqlXmlText;

    // 格式化好的sql文本
    String                      parserSqlText;

    // 是否是动态sql
    boolean                     isDynamicSql = false;

    // 是否含有include
    boolean                     isIncludeSql = false;

    // sqlType
    String                      sqlType;

    // dbtype
    String                      dbType;

    // property的cacheMap
    Map<String, String>         propertyCacheMap;

    // include的map,外部调用
    Map<String, DefaultElement> includeNodeMap;

    // xmltype
    String                      xmlType;

    public GetParseSql(String rawSqlXmlText, String sqlType, String dbType, Map<String, DefaultElement> includeNodeMap){
        this.rawSqlXmlText = rawSqlXmlText;
        this.sqlType = sqlType;
        this.dbType = dbType;
        this.includeNodeMap = includeNodeMap;
        propertyCacheMap = new HashMap<String, String>();
    }

    public GetParseSql(String rawSqlXmlText, String xmlType, String sqlType, String dbType,
                       Map<String, DefaultElement> includeNodeMap){
        this.rawSqlXmlText = rawSqlXmlText;
        this.xmlType = xmlType;
        this.sqlType = sqlType;
        this.dbType = dbType;
        this.includeNodeMap = includeNodeMap;
        propertyCacheMap = new HashMap<String, String>();
    }

    /**
     * 获取sql文本
     */
    public String getSqlXml() {

        String formatSql = "";
        // 去掉多余的回车空格
        formatSql = SqlFormatUtils.delSpace(rawSqlXmlText);
        // 去掉多余的空格
        formatSql = SqlFormatUtils.delByPattern(formatSql);
        return formatSql;
    }

    /**
     * 解析sql文本，获取结构化数据
     */
    public ParserMetaResult getIbatisStrutce(SqlMapNodes sqlMapNodesDO) throws Exception {

        ParserMetaResult parserResult = new ParserMetaResult();
        String sqlXml = getSqlXml();
        // 解析xml
        SAXReader saxReader = new SAXReader();
        saxReader.setValidation(false);
        saxReader.setEntityResolver(new NoOpEntityResolver());
        Document document;

        String sql = "";

        try {
            document = saxReader.read(new StringReader(sqlXml));
            Element rootElement = document.getRootElement();
            // 获取类型
            String nodeType = rootElement.getName();
            if (Commons.sqlTypeList.contains(nodeType)) {
                // 设置sql类型
                parserResult.setSqlType(sqlType);
                parserResult.setXmlType(Commons.SQLMAP);
                // 获取sqlId
                String sqlId = rootElement.attributeValue("id");

                if (!StringUtils.isEmpty(sqlId)) {
                    parserResult.setSqlId(sqlId);
                    List contentList = rootElement.content();
                    // 解析xml内部内容并判断是不是动态sql
                    List<INodeElement> nodeParserResultList = handleIbatisXmlContent(contentList);
                    // 打印sql
                    StringBuilder parserSqlStringBuilder = new StringBuilder();
                    for (INodeElement nodeParserResult : nodeParserResultList) {
                        parserSqlStringBuilder.append(nodeParserResult.toString());
                    }
                    parserSqlText = parserSqlStringBuilder.toString();
                    // 设置格式化好的sql
                    sql = getSqlStrutce(parserSqlText);
                    if (sql.toLowerCase().startsWith("insert")) {
                        sql = sql.replaceAll("#", "'");
                    } else {
                        sql = beforegetIbaitsSqlStrutce(sql);
                    }
                    parserResult.setPostSqlString(sql);
                    // 是否是动态sql
                    parserResult.setDynamicSql(isDynamicSql);
                    parserResult.setIncludeSql(isIncludeSql);

                    // FIXME: 这里好像有个BUG，这个应该传dbType，不是sqlType
                    SchemaStatVisitor visitor = DruidUtils.getSqlParserVisitor(sql.toLowerCase(), dbType);

                    DruidUtils.getTables(visitor);
                    DruidUtils.getConditions(visitor);
                    parserResult.setPreSqlXml(rawSqlXmlText);
                    parserResult.setNameSpace(sqlMapNodesDO.getNameSpace());
                    parserResult.setXmlComment(sqlMapNodesDO.getSqlComments());
                    // FIXME: 先写了一个不会报错的方法，Mock一下
                    // parserResult.setFormatSql(SQLUtils.format(parserResult.getPostSqlString(), sqlType));
                    parserResult.setFormatSql(ZHMSQLUtil.format(parserResult.getPostSqlString(), sqlType));
                } else {
                    throw new Exception(Commons.E_NULL_SQL_ID);
                }

                // FIXME:这里暂时不支持的节点类型会直接抛错，所以先返回一些提示字符

            } else {
                // throw new Exception(Commons.E_SQLTYPE_NOT_SUPPORT);
                // parserResult.setFormatSql("暂时不支持" + nodeType + "类型.");
                return parserResult;
            }
        } catch (Exception e) {
            // FIXME: 这里先记录一下，get_group_chat_messages_for_max_limit 这个id，会变成两个limit，有bug
            // throw new Exception(Commons.E_PARSE_XML_FAIL);
            // FIXME: 这里还有一个BUG，目前druid对oracle的支持有限，如果解析失败了，先把sql原样返回吧
            parserResult.setFormatSql(sql);
            // parserResult.setFormatSql("解析错误");
            return parserResult;
        }
        return parserResult;
    }

    /**
     * 获取mybatis的sql结构
     */
    public ParserMetaResult getMyBatisStruce(SqlMapNodes sqlMapNodesDO) throws Exception {
        ParserMetaResult parserResult = new ParserMetaResult();

        String sqlXml = getSqlXml();
        // 解析xml
        SAXReader saxReader = new SAXReader();
        saxReader.setValidation(false);
        saxReader.setEntityResolver(new NoOpEntityResolver());
        Document document;
        
        String sql = "";
        
        try {
            document = saxReader.read(new StringReader(sqlXml));
            Element rootElement = document.getRootElement();
            // 获取类型
            String nodeType = rootElement.getName();
            if (Commons.sqlTypeList.contains(nodeType)) {
                // 设置sql类型
                parserResult.setSqlType(sqlType);
                parserResult.setXmlType(Commons.MAPPER);
                // 获取sqlId
                String sqlId = rootElement.attributeValue("id");
                if (!StringUtils.isEmpty(sqlId)) {
                    parserResult.setSqlId(sqlId);
                    List contentList = rootElement.content();
                    // 解析xml内部内容并判断是不是动态sql
                    List<INodeElement> nodeParserResultList = handleMybatisXmlContent(contentList);
                    // 打印sql
                    StringBuilder parserSqlStringBuilder = new StringBuilder();
                    for (INodeElement nodeParserResult : nodeParserResultList) {
                        parserSqlStringBuilder.append(nodeParserResult.toString());
                    }
                    parserSqlText = parserSqlStringBuilder.toString();
                    // 设置格式化好的sql
                    sql = getSqlStrutce(parserSqlText);
                    if (sql.toLowerCase().startsWith("insert")) {
                        // FIXME: 由于mybatis的变量为#{xxx}，这里需要改一下
                        sql = sql.replaceAll("#\\{", "'").replaceAll("\\}", "'");
                    }
                    // 设置格式化好的sql
                    parserResult.setPostSqlString(sql);
                    // 是否是动态sql
                    parserResult.setDynamicSql(isDynamicSql);
                    parserResult.setIncludeSql(isIncludeSql);
                    SchemaStatVisitor visitor = DruidUtils.getSqlParserVisitor(sql, dbType);
                    DruidUtils.getTables(visitor);
                    DruidUtils.getConditions(visitor);
                    parserResult.setPreSqlXml(rawSqlXmlText);
                    parserResult.setNameSpace(sqlMapNodesDO.getNameSpace());
                    parserResult.setXmlComment(sqlMapNodesDO.getSqlComments());
                    parserResult.setFormatSql(SQLUtils.format(parserResult.getPostSqlString(), sqlType));
                } else {
                    throw new Exception(Commons.E_NULL_SQL_ID);
                }
            }
            // FIXME:这里暂时不支持的节点类型会直接抛错，所以先返回一些提示字符
            else {
                // throw new Exception(Commons.E_SQLTYPE_NOT_SUPPORT);
                // parserResult.setFormatSql("暂时不支持" + nodeType + "类型.");
                return parserResult;
            }
        } catch (Exception e) {
            // FIXME: 这里先记录一下，get_group_chat_messages_for_max_limit 这个id，会变成两个limit，有bug
            // throw new Exception(Commons.E_PARSE_XML_FAIL);
            // FIXME: 这里还有一个BUG，目前druid对oracle的支持有限，如果解析失败了，先把sql原样返回吧
            parserResult.setFormatSql(sql);
            // parserResult.setFormatSql("解析错误");
            return parserResult;
        }
        return parserResult;
    }

    /**
     * 解析XML为结构化数据,获取sql,是否为动态sql及格式化结果,ibatis代码调用
     */
    private List<INodeElement> handleIbatisXmlContent(List contentList) throws Exception {
        List<INodeElement> parserNodeResult = new ArrayList<INodeElement>();

        for (Object content : contentList) {
            INodeElement element = null;
            // 字符处理
            if (content instanceof DefaultText) {
                element = new TextNode();
                if (!StringUtils.isEmpty(((DefaultText) content).getStringValue())) {
                    ((TextNode) element).setTextValue(((DefaultText) content).getStringValue());
                } else {
                    ((TextNode) element).setTextValue(Commons.S_SPACE_STRING);
                }
                // cdata处理
            } else if (content instanceof DefaultCDATA) {
                element = new CdataNode();
                ((CdataNode) element).setStringValue(Commons.S_SPACE_STRING + ((DefaultCDATA) content).getText().trim()
                                                     + Commons.S_SPACE_STRING);
                // 节点处理
            } else if (content instanceof DefaultElement) {
                String nodeType = ((DefaultElement) content).getName();
                if (Commons.nodeMap.containsKey(nodeType.toLowerCase())) {
                    String clazzName = Commons.nodeMap.get(nodeType.toLowerCase());
                    if (!clazzName.equals("")) {
                        element = (INodeElement) Class.forName(clazzName).newInstance();
                    }
                    // 设置proerty
                    if (((DefaultElement) content).attribute("property") != null) {
                        // 存在propertycatch中
                        String property = ((DefaultElement) content).attribute("property").getValue().trim();
                        if (propertyCacheMap.containsKey(property)) {
                            element.setConfilct(nodeType);
                        } else {
                            propertyCacheMap.put(property, nodeType);
                        }
                        element.setProperty(((DefaultElement) content).attribute("property").getValue().trim());
                    }
                    // 设置prepend
                    if (((DefaultElement) content).attribute("prepend") != null) {
                        element.setPrepend(((DefaultElement) content).attribute("prepend").getValue().trim());
                    }
                    // 设置comparePro
                    if (((DefaultElement) content).attribute("compareProperty") != null) {
                        element.setCompareProperty(((DefaultElement) content).attribute("compareProperty").getValue().trim());
                    }
                    // 设置compareValue
                    if (((DefaultElement) content).attribute("compareValue") != null) {
                        element.setCompareValue(((DefaultElement) content).attribute("compareValue").getValue().trim());
                    }
                    // 处理下iterator的情况
                    if (((DefaultElement) content).attribute("open") != null) {
                        element.setOpen(((DefaultElement) content).attribute("open").getValue().trim());
                    }
                    if (((DefaultElement) content).attribute("close") != null) {
                        element.setClose(((DefaultElement) content).attribute("close").getValue().trim());
                    }
                    if (((DefaultElement) content).attribute("conjunction") != null) {
                        element.setConjunction(((DefaultElement) content).attribute("conjunction").getValue().trim());
                    }

                    // 处理iterate
                    if (nodeType.equalsIgnoreCase("ITERATE")) {
                        isDynamicSql = true;
                        List soncontents = ((DefaultElement) content).content();
                        List sonParseResult = handleIbatisXmlContent(soncontents);
                        element.setSonParseResult(sonParseResult);
                    }
                    // 处理include
                    else if (nodeType.equalsIgnoreCase("INCLUDE")) {
                        isIncludeSql = true;
                        String includeId = ((DefaultElement) content).attributeValue("refid");
                        includeId = getNoNameSpaceId(includeId);
                        Element includeElement = includeNodeMap.get(includeId);
                        if (includeElement != null) {
                            StringBuilder includeNodeXmlStringBuilder = new StringBuilder();
                            for (Object includeSonContent : includeElement.content()) {
                                if (includeSonContent instanceof DefaultText) {
                                    includeNodeXmlStringBuilder.append(((DefaultText) includeSonContent).asXML());
                                } else if (includeSonContent instanceof DefaultCDATA) {
                                    includeNodeXmlStringBuilder.append(((DefaultCDATA) includeSonContent).getText().trim()
                                                                       + Commons.S_SPACE_STRING);
                                } else if (includeSonContent instanceof DefaultComment) {
                                    // 注释do nothing
                                } else {
                                    includeNodeXmlStringBuilder.append(((Element) includeSonContent).asXML());
                                }
                            }
                            rawSqlXmlText = rawSqlXmlText.replace(((DefaultElement) content).asXML(),
                                                                  includeNodeXmlStringBuilder.toString());
                            List includeContents = includeElement.content();
                            List sonParseResult = handleIbatisXmlContent(includeContents);
                            element.setSonParseResult(sonParseResult);
                        } else {
                            throw new Exception(Commons.E_NO_INCLUDEID);
                        }
                    }
                    // 处理dynamic
                    else if (((DefaultElement) content).getName().equalsIgnoreCase("DYNAMIC")) {
                        // String strCon = content.toString();
                        isDynamicSql = true;
                        List soncontents = ((DefaultElement) content).content();
                        List sonParseResult = handleIbatisXmlContent(soncontents);
                        element.setSonParseResult(sonParseResult);
                    }
                    // 处理selectkey
                    else if (((DefaultElement) content).getName().equalsIgnoreCase("selectKey")) {
                        continue;
                        // 其他情形
                    } else {
                        isDynamicSql = true;
                        List soncontents = ((DefaultElement) content).content();
                        List sonParseResult = handleIbatisXmlContent(soncontents);
                        element.setSonParseResult(sonParseResult);
                    }
                } else {
                    throw new Exception(Commons.E_PARSE_NODE_FAIL);
                }
            } else if (content instanceof DefaultComment) {
                // 注释不操作
            } else {
                throw new Exception(Commons.E_PARSE_NODE_FAIL);
            }
            if (element != null) {
                parserNodeResult.add(element);
            }
        }
        return parserNodeResult;
    }

    /**
     * 解析XML为结构化数据,获取sql,是否为动态sql及格式化结果,mybatis代码调用
     */
    private List<INodeElement> handleMybatisXmlContent(List contentList) throws Exception {
        List<INodeElement> parserNodeResult = new ArrayList<INodeElement>();

        for (Object content : contentList) {
            INodeElement element = null;
            // 字符处理
            if (content instanceof DefaultText) {
                element = new TextNode();
                if (!StringUtils.isEmpty(((DefaultText) content).getStringValue())) {
                    ((TextNode) element).setTextValue(((DefaultText) content).getStringValue());
                } else {
                    ((TextNode) element).setTextValue(Commons.S_SPACE_STRING);
                }
                // cdata处理
            } else if (content instanceof DefaultCDATA) {
                element = new CdataNode();
                ((CdataNode) element).setStringValue(Commons.S_SPACE_STRING + ((DefaultCDATA) content).getText().trim()
                                                     + Commons.S_SPACE_STRING);
                // 节点处理
            } else if (content instanceof DefaultElement) {
                String nodeType = ((DefaultElement) content).getName();
                if (Commons.mybatisNodeMap.containsKey(nodeType.toLowerCase())) {
                    String clazzName = Commons.mybatisNodeMap.get(nodeType.toLowerCase());
                    if (!clazzName.equals("")) {
                        element = (INodeElement) Class.forName(clazzName).newInstance();
                    }
                    // 设置test
                    if (((DefaultElement) content).attribute("test") != null) {
                        String test = ((DefaultElement) content).attribute("test").getValue().trim();
                        element.setTest(test);
                    }
                    // trim prefix="WHERE" prefixOverrides="AND |OR "
                    if (((DefaultElement) content).attribute("prefix") != null) {
                        element.setPrefix(((DefaultElement) content).attribute("prefix").getValue().trim());
                    }
                    if (((DefaultElement) content).attribute("suffix") != null) {
                        element.setSuffix(((DefaultElement) content).attribute("suffix").getValue().trim());
                    }
                    // trim prefix="WHERE" prefixOverrides="AND |OR "
                    if (((DefaultElement) content).attribute("prefixOverrides") != null) {
                        element.setPrefixOverrides(((DefaultElement) content).attribute("prefixOverrides").getValue().trim());
                    }
                    // trim prefix="SET" suffixOverrides=","
                    if (((DefaultElement) content).attribute("suffixOverrides") != null) {
                        element.setSuffixOverrides(((DefaultElement) content).attribute("suffixOverrides").getValue().trim());
                    }
                    // foreach
                    if (((DefaultElement) content).attribute("item") != null) {
                        element.setItem(((DefaultElement) content).attribute("item").getValue().trim());
                    }
                    if (((DefaultElement) content).attribute("index") != null) {
                        element.setIndex(((DefaultElement) content).attribute("index").getValue().trim());
                    }
                    if (((DefaultElement) content).attribute("collection") != null) {
                        element.setCollection(((DefaultElement) content).attribute("collection").getValue().trim());
                    }
                    if (((DefaultElement) content).attribute("open") != null) {
                        element.setOpen(((DefaultElement) content).attribute("open").getValue().trim());
                    }
                    if (((DefaultElement) content).attribute("close") != null) {
                        element.setClose(((DefaultElement) content).attribute("close").getValue().trim());
                    }
                    if (((DefaultElement) content).attribute("separator") != null) {
                        element.setSaparator(((DefaultElement) content).attribute("separator").getValue().trim());
                    }
                    // 处理include
                    if (nodeType.equalsIgnoreCase("INCLUDE")) {
                        isIncludeSql = true;
                        String includeId = ((DefaultElement) content).attributeValue("refid");
                        includeId = getNoNameSpaceId(includeId);
                        Element includeElement = includeNodeMap.get(includeId);
                        if (includeElement != null) {
                            StringBuilder includeNodeXmlStringBuilder = new StringBuilder();
                            for (Object includeSonContent : includeElement.content()) {
                                if (includeSonContent instanceof DefaultText) {
                                    includeNodeXmlStringBuilder.append(((DefaultText) includeSonContent).asXML());
                                } else if (includeSonContent instanceof DefaultCDATA) {
                                    includeNodeXmlStringBuilder.append(((DefaultCDATA) includeSonContent).getText().trim()
                                                                       + Commons.S_SPACE_STRING);
                                } else if (includeSonContent instanceof DefaultComment) {
                                    // 注释do nothing
                                } else {
                                    includeNodeXmlStringBuilder.append(((Element) includeSonContent).asXML());
                                }
                            }
                            rawSqlXmlText = rawSqlXmlText.replace(((DefaultElement) content).asXML(),
                                                                  includeNodeXmlStringBuilder.toString());
                            List includeContents = includeElement.content();
                            List sonParseResult = handleMybatisXmlContent(includeContents);
                            element.setSonParseResult(sonParseResult);
                        } else {
                            throw new Exception(Commons.E_NO_INCLUDEID);
                        }
                    } else if (nodeType.equalsIgnoreCase("BIND")) {
                        // DONOTING
                    } else if (nodeType.equalsIgnoreCase("SELECTKEY")) {
                        // DONOTHIN
                    } else {
                        isDynamicSql = true;
                        List soncontents = ((DefaultElement) content).content();
                        List sonParseResult = handleMybatisXmlContent(soncontents);
                        element.setSonParseResult(sonParseResult);
                    }
                } else {
                    throw new Exception(Commons.E_PARSE_NODE_FAIL);
                }
            } else if (content instanceof DefaultComment) {
                // donothing
            } else {
                throw new Exception(Commons.E_PARSE_NODE_FAIL);
            }
            if (element != null) {
                parserNodeResult.add(element);
            }
        }
        return parserNodeResult;
    }

    /**
     * 获取格式化好的sql
     */
    public String getSqlStrutce(String parserSqlText) {
        // 去掉多余的回车空格
        parserSqlText = SqlFormatUtils.formatTheSqlString(parserSqlText);
        return parserSqlText;
    }

    /**
     * 获取格式化好的sql
     */
    public String beforegetIbaitsSqlStrutce(String parserSqlText) {
        // 处理##
        parserSqlText = SqlFormatUtils.formatdruidSharp(parserSqlText);
        return parserSqlText;
    }

    /**
     * mybatis额外处理参数的sql
     */
    // public String getMybatisSqlStrutce(String parserSqlText) {
    // parserSqlText = getSqlStrutce(parserSqlText);
    // //去除大括号
    // parserSqlText = parserSqlText.replaceAll("\\{", "");
    // parserSqlText = parserSqlText.replaceAll("}", "#");
    // return parserSqlText;
    // }

    /**
     * 去掉id前面的namespace
     */
    public static String getNoNameSpaceId(String id) {
        // 修复bug
        if (id.contains(".")) {
            return id.substring(id.indexOf(".") + 1);
        } else {
            return id;
        }
    }

    /**
     * 去掉id前面的namespace
     */
    public static String getNoNameSpaceId(String id, String sqlMapnameSpace) {
        // 修复bug
        if (sqlMapnameSpace != null && !sqlMapnameSpace.equals("") && id.contains(sqlMapnameSpace + ".")) {
            return id.substring(id.indexOf(sqlMapnameSpace + ".") + sqlMapnameSpace.length() + 1);
        } else {
            return id;
        }
    }

}
