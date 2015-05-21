package com.alibaba.bigData.ibatisparser.engine;

import com.alibaba.bigData.ibatisparser.configuration.Commons;
import com.alibaba.bigData.ibatisparser.objects.SqlMapNodes;
import com.alibaba.bigData.ibatisparser.utils.NoOpEntityResolver;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA. User: xiaoming.wm Date: 14-2-14 Time: 下午5:42 解析XML文件获取sql的结构
 */
public class ParseFileContent {

    public static final String INCLUDE_MAP_NODE = "includeMapNode";
    public static final String SQL_MAP_NODE     = "sqlMapNode";

    /**
     * 传入XML文件，取得所有的xml节点
     */
    public Map<String, Object> getAllSqls(String is) throws Exception {

        Map<String, Element> includeMap = new HashMap<String, Element>();

        Map<String, SqlMapNodes> sqlMap = new HashMap<String, SqlMapNodes>();

        Map<String, Object> result = new HashMap<String, Object>();
        try {
            SAXReader saxReader = new SAXReader();
            saxReader.setValidation(false);
            saxReader.setEntityResolver(new NoOpEntityResolver());
            Document document;
            document = saxReader.read(new StringReader(is));
            Element sqlMapElement = document.getRootElement();

            if (sqlMapElement.getName().equalsIgnoreCase(Commons.SQLMAP)
                || sqlMapElement.getName().equalsIgnoreCase(Commons.MAPPER)) {
                String mapType = sqlMapElement.getName().toUpperCase();
                // 获取命名空间
                String namespace = sqlMapElement.attributeValue("namespace");
                // 注释
                String comments = "";
                // 获取include

                List<Element> sqlElementsList = sqlMapElement.elements("sql");
                for (Element sqlElement : sqlElementsList) {
                    String includeSqlId = sqlElement.attributeValue("id");
                    includeSqlId = GetParseSql.getNoNameSpaceId(includeSqlId);
                    includeMap.put(includeSqlId, sqlElement);
                }
                // 获取迭代器
                Iterator<Node> nodeIterator = sqlMapElement.nodeIterator();
                while (nodeIterator.hasNext()) {
                    Node node = nodeIterator.next();
                    if (node.getNodeType() == Node.COMMENT_NODE) {
                        comments = node.getText();
                    } else if (node.getNodeType() == Node.ELEMENT_NODE
                               && (node.getName().equalsIgnoreCase(Commons.SELECT)
                                   || node.getName().equalsIgnoreCase(Commons.UPDATE)
                                   || node.getName().equalsIgnoreCase(Commons.INSERT)
                                   || node.getName().equalsIgnoreCase(Commons.DELETE) || node.getName().equalsIgnoreCase(Commons.SQL))) {
                        SqlMapNodes sqlMapNodesDO = new SqlMapNodes();
                        // 设置注释
                        sqlMapNodesDO.setSqlComments(comments);
                        comments = "";
                        // 设置命名空间
                        sqlMapNodesDO.setNameSpace(namespace);
                        // 设置类型
                        String sqlType = node.getName();
                        sqlMapNodesDO.setSqlType(sqlType);
                        // 获取id
                        String sqlId = ((Element) node).attributeValue("id");
                        sqlId = GetParseSql.getNoNameSpaceId(sqlId);
                        sqlMapNodesDO.setSqlClassId(sqlId);
                        // 获取xml
                        String xml = node.asXML();
                        sqlMapNodesDO.setSqlXml(xml);
                        sqlMapNodesDO.setMapType(mapType);
                        sqlMap.put(sqlId, sqlMapNodesDO);
                    } else if (node.getNodeType() == Node.TEXT_NODE && node.getText().trim().equals("")) {
                        // 处理空白字段
                    } else if (node.getNodeType() == Node.ELEMENT_NODE
                               && (node.getName().equalsIgnoreCase(Commons.TYPEALIAS) || node.getName().equalsIgnoreCase(Commons.RESULTMAP))) {
                        // 处理映射节点
                        comments = "";
                    } else {
                        // FIXME: cacheModel节点不能识别
                        // System.err.print("unknow type node");
                        comments = "";
                    }
                }
                result.put(INCLUDE_MAP_NODE, includeMap);
                result.put(SQL_MAP_NODE, sqlMap);
            } else {
                throw new Exception("该文件不是ibatis或mybatis文件");
            }
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return result;
    }

}
