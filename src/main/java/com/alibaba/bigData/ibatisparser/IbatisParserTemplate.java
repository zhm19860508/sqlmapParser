package com.alibaba.bigData.ibatisparser;

import com.alibaba.bigData.ibatisparser.configuration.Commons;
import com.alibaba.bigData.ibatisparser.configuration.Configuration;
import com.alibaba.bigData.ibatisparser.engine.GetParseSql;
import com.alibaba.bigData.ibatisparser.engine.ParseFileContent;
import com.alibaba.bigData.ibatisparser.objects.SqlMapNodes;
import com.alibaba.bigData.ibatisparser.sqlmeta.ParserMetaResult;

import org.dom4j.tree.DefaultElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-18
 * Time: 上午10:40
 * To change this template use File | Settings | File Templates.
 * 解析的主类
 */
public class IbatisParserTemplate {

    //配置类
    Configuration configuration;

    //原始的xml文本
    String xmlText;

    /**
     * 初始化方法，用来初始化一些配置
     */
    public void init(Configuration conf,String xmlText) {

        this.configuration=conf;
        this.xmlText=xmlText;
    }

    /**
     * 执行方法
     */
    public List<ParserMetaResult> run() throws Exception {
        List<ParserMetaResult> resultList=new ArrayList<ParserMetaResult>();
        //解析对应的xml文件
        ParseFileContent parseFile=new ParseFileContent();
        Map<String,Object> parXmlResult=parseFile.getAllSqls(xmlText);
        Map<String, DefaultElement> includeMaps= (Map<String, DefaultElement>) parXmlResult.get(ParseFileContent.INCLUDE_MAP_NODE);
        Map<String,SqlMapNodes> sqlMaps= (Map<String, SqlMapNodes>) parXmlResult.get(ParseFileContent.SQL_MAP_NODE);

        for(SqlMapNodes sqlMapNodesDO:sqlMaps.values()){
            try{
                GetParseSql getParseSql=new GetParseSql(sqlMapNodesDO.getSqlXml(),sqlMapNodesDO.getMapType(),configuration.getSqlType(),includeMaps);
                if(sqlMapNodesDO.getMapType().equals(Commons.MAPPER)){
                    ParserMetaResult parserMetaResult=getParseSql.getMyBatisStruce(sqlMapNodesDO);
                    resultList.add(parserMetaResult);
                }else if(sqlMapNodesDO.getMapType().equals(Commons.SQLMAP)){
                    ParserMetaResult parserMetaResult=getParseSql.getIbatisStrutce(sqlMapNodesDO);
                    resultList.add(parserMetaResult);
                }else {
                    throw new Exception(sqlMapNodesDO.getSqlClassId()+"is not a right node");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return resultList;
    }

}