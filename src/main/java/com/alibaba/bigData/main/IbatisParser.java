package com.alibaba.bigData.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

import com.alibaba.bigData.ibatisparser.engine.GetParseSql;
import com.alibaba.bigData.ibatisparser.engine.ParseFileContent;
import com.alibaba.bigData.ibatisparser.objects.SqlMapNodes;
import com.alibaba.bigData.ibatisparser.sqlmeta.ParserMetaResult;
import com.alibaba.bigData.ibatisparser.utils.NoOpEntityResolver;
import com.alibaba.fastjson.JSON;

public class IbatisParser {

    // 从SQLMap文件中获取所有的SQL
    public String beyondGetAllSqls(String dirPath, String dbType, String sqlmapType, String logPath) {
        String result = "";
        try {
            result = getAllSqls(dirPath, dbType, logPath, sqlmapType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private String getAllSqls(String dirPath, String dbType, String logPath, String sqlmapType) throws Exception {
        if (StringUtils.isBlank(dirPath) || StringUtils.isBlank(dbType)) {
            return "";
        }

        List<String> files = new ArrayList<String>();

        String jsonResult = "";

        getFiles(dirPath, files);

        List<ParseResult> result = new ArrayList<ParseResult>();

        for (String string : files) {

            String sql = "";

            sql = getXmlString(string);

            if (StringUtils.isBlank(sql)) {
                continue;
            }

            ParseFileContent content = new ParseFileContent();
            Map<String, Object> map = content.getAllSqls(sql);

            Map<String, SqlMapNodes> map2 = (Map<String, SqlMapNodes>) map.get("sqlMapNode");
            Map<String, DefaultElement> includeMapNode = (Map<String, DefaultElement>) map.get("includeMapNode");

            // 先预处理一遍sqlMapNodes,有的sql有isNull，又有isNotNull，把这样的sql拆成两个，一个去除全部isNull节点，另一个去除所有isNotNull节点
            Map<String, SqlMapNodes> mapYuchuli = new HashMap<>();
            for (String key : map2.keySet()) {
                SqlMapNodes sqlMapNodesDO = map2.get(key);
                String sqlXml = sqlMapNodesDO.getSqlXml();
                if (sqlXml.contains("isNotNull") && sqlXml.contains("isNull")) {
                    // 删除isNull节点
                    Document document = DocumentHelper.parseText(sqlXml);
                    List<Element> elesNull = document.getRootElement().elements("isNull");
                    for (Element element : elesNull) {
                        document.getRootElement().remove(element);
                    }
                    String xmlStr = document.asXML();
                    sqlMapNodesDO.setSqlXml(xmlStr);
                    mapYuchuli.put(key, sqlMapNodesDO);
                    // 删除isNotNull节点
                    document = DocumentHelper.parseText(sqlXml);
                    List<Element> elesNotNull = document.getRootElement().elements("isNotNull");
                    for (Element element : elesNotNull) {
                        document.getRootElement().remove(element);
                    }
                    String xmlStr2 = document.asXML();
                    SqlMapNodes sqlMapNodesDO2 = (SqlMapNodes) sqlMapNodesDO.clone();
                    sqlMapNodesDO2.setSqlXml(xmlStr2);
                    mapYuchuli.put(key + "_2", sqlMapNodesDO2);
                    continue;
                }

                mapYuchuli.put(key, sqlMapNodesDO);
            }

            for (String key : mapYuchuli.keySet()) {
                SqlMapNodes sqlMapNodesDO = mapYuchuli.get(key);
                GetParseSql getParseSql = new GetParseSql(sqlMapNodesDO.getSqlXml(), sqlMapNodesDO.getSqlType(),
                                                          dbType, includeMapNode);
                ParserMetaResult parserMetaResult;
                if (sqlmapType.equals("ibatis")) {
                    parserMetaResult = getParseSql.getIbatisStrutce(sqlMapNodesDO);
                } else {
                    parserMetaResult = getParseSql.getMyBatisStruce(sqlMapNodesDO);
                }
                // FIXME: 有的类型还不支持。如果这里返回了不支持的类型，就不要输出了
                ParseResult r = new ParseResult();
                if (!StringUtils.isBlank(parserMetaResult.getFormatSql())) {
                    r.setSqlId(key);
                    r.setSqlStr(parserMetaResult.getFormatSql().replace("\n", " ").replace("\t", " ").replace("  ", " "));
                    result.add(r);
                }
            }

        }

        for (ParseResult parseResult : result) {
            System.err.println(parseResult.getSqlId() + ":" + parseResult.getSqlStr());
        }

        // 写入文件
        // log(result, logPath);

        jsonResult = JSON.toJSONString(result);
        return jsonResult;

    }

    private void log(List<ParseResult> result, String logPath) {
        if (result.size() <= 0 || StringUtils.isBlank(logPath)) {
            return;
        }
        File logFile = new File(logPath);
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logFile));
            for (ParseResult parseResult : result) {
                bufferedWriter.append(parseResult.getSqlStr() + "\n\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getXmlString(String filePath) {
        SAXReader reader = new SAXReader();
        reader.setValidation(false);
        reader.setEntityResolver(new NoOpEntityResolver());
        File sqlmapFile = new File(filePath);
        String xmlStr = "";
        try {
            Document d = reader.read(sqlmapFile);
            xmlStr = d.asXML();
        } catch (Exception e) {

        }
        return xmlStr;
    }

    private List<String> getFiles(String filePath, List<String> filelist) {
        File root = new File(filePath);
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                /*
                 * 递归调用,xml文件才添加进去
                 */
                getFiles(file.getAbsolutePath(), filelist);
                if (file.getName().contains(".xml")) {
                    filelist.add(file.getAbsolutePath());
                }
            } else {
                if (file.getName().contains(".xml")) {
                    filelist.add(file.getAbsolutePath());
                }
            }
        }
        return filelist;
    }
}
