package com.alibaba.bigData.indexmaker;

import com.alibaba.bigData.ibatisparser.utils.SqlFormatUtils;
import com.alibaba.bigData.indexmaker.configuration.Configuration;
import com.alibaba.bigData.indexmaker.configuration.SqlType;
import com.alibaba.bigData.indexmaker.engine.SqlCheckEngine;
import com.alibaba.bigData.indexmaker.objects.*;
import com.alibaba.bigData.indexmaker.rules.ParseSqlRule;
import com.alibaba.bigData.indexmaker.sqlmeta.*;
import com.alibaba.bigData.indexmaker.utils.DruidUtils;
import com.alibaba.bigData.indexmaker.utils.SqlUtils;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-12-6
 * Time: 下午3:20
 * IndexMaker的核心类
 */
public class IndexMakerTemplate {

    //sch名称
    String schName = "";

    //原生类型
    String sql;

    //Sql类型
    SqlType sqlType;

    //数据库连接
    Connection connection;

    //是否是动态sql
     String isDanamicSql;

    //rulecheck
    RuleCheckedDO  idbRuleCheckDO;

    //idb的表信息
    IdbDbInfor idbDbInfroDO;


    /**
     * 初始化方法
     */
    public void  init(IndexMakerPara indexMakerPara) {
        this.connection = indexMakerPara.getDbConnection();
        this.sql = indexMakerPara.getInputSql();
        this.schName = indexMakerPara.getSchName();
        this.sqlType = indexMakerPara.getDbType();
        this.isDanamicSql= indexMakerPara.getDynamicSql();
        this.idbRuleCheckDO= indexMakerPara.getRuleCheckedDO();
        this.idbDbInfroDO=indexMakerPara.getIdbDbInfroDO();
    }

    /**
     * 获取sqlmetadata
     */
    public SqlMetaDataInfor getSqlMetaDataInfor(String sql,String dbType) throws Exception {
        sql= SqlFormatUtils.formatTheSqlString(sql);
        SchemaStatVisitor visitor = DruidUtils.getSqlParserVisitor(sql, dbType);
        Map<TableStat.Name, TableStat> tableStatMap = DruidUtils.getTables(visitor);
        List<TableStat.Condition> conditionList = DruidUtils.getConditions(visitor);
        List<TableStat.Column> orderList= DruidUtils.getOrders(visitor);
        SqlMetaDataInfor sqlMetaDataInfor = new SqlMetaDataInfor();
        sqlMetaDataInfor.setTableStatMap(tableStatMap);
        sqlMetaDataInfor.setConditionList(conditionList);
        sqlMetaDataInfor.setOrderList(orderList);
        //获取真实表名
        Map<TableStat.Name, String> realTableNameMap=new HashMap<TableStat.Name, String>();
        for (TableStat.Name table : tableStatMap.keySet()) {
            String tableName = table.getName();
            String realTableName=SqlUtils.getRealTableName( tableName, sqlType, schName, connection);
            realTableNameMap.put(table,realTableName);
        }
        sqlMetaDataInfor.setRealTableNameMap(realTableNameMap);
        return sqlMetaDataInfor;
    }


    /**
     * 执行indexmaker
     */
    public List<IndexMakerResult> run() throws Exception {
        List<IndexMakerResult> indexMakerResults = new ArrayList<IndexMakerResult>();
        //解析SQL
        SqlMetaDataInfor sqlMetaDataInfor=getSqlMetaDataInfor(sql,sqlType.toString());
        //获取表名结合
        Map<TableStat.Name, TableStat> tables = sqlMetaDataInfor.getTableStatMap();

        //依次处理多个表
        for (TableStat.Name table : tables.keySet()) {
            IndexMakerResult indexMakerResult=new IndexMakerResult();
            String tableName = table.getName();
            String realTableName=sqlMetaDataInfor.getRealTableNameMap().get(table);
            if (tableName != null&&realTableName!=null) {
                //获取表下面的元数据
                List<SqlInforColumn> metaDataList = SqlUtils.getMetaData(schName,sqlType ,realTableName,connection);
                //获取已有索引
                List<SqlInforIndex> indexList = SqlUtils.getIndexInfor(schName, realTableName,sqlType,connection);
                //获取执行计划
                SqlExplainResult explainResult;
                try{
                    explainResult = SqlUtils.getSqlExpalin(connection,schName, realTableName,sqlType, sql,sqlMetaDataInfor);
                }catch (Exception e){
                    List<RuleCheckResult> engineResult=new ArrayList<RuleCheckResult>();
                    explainResult = new SqlExplainResult();
                    explainResult.setSql(sql);
                    explainResult.setSqlWithPara(sql);
                    explainResult.setSqlWithData("");
                    engineResult.add(new ParseSqlRule().parserSqlCheck(e));
                    indexMakerResult.setSqlEngineResult(engineResult);
                }
                //获取表大小
                SqlTableInfo tabDO = SqlUtils.getTableInfo(schName,sqlType ,realTableName,connection);
                try{
                    //sql校验引擎
                    Configuration configuration = new Configuration(idbRuleCheckDO);
                    SqlCheckEngine sqlCheckEngine=new SqlCheckEngine(schName,sql,sqlType,configuration,sqlMetaDataInfor,connection,explainResult,metaDataList,indexList,tabDO,isDanamicSql,idbRuleCheckDO);
                    List<RuleCheckResult> engineResult=sqlCheckEngine.checkSql();
                    indexMakerResult.setSqlEngineResult(engineResult);
                }catch (Exception e){
                    List<RuleCheckResult> engineResult=new ArrayList<RuleCheckResult>();
                    engineResult.add(new ParseSqlRule().parserSqlCheck(e));
                    indexMakerResult.setSqlEngineResult(engineResult);
                }
                //存储结果
                indexMakerResult.setTableName(tableName);
                indexMakerResult.setDbType(sqlType.toString());
                indexMakerResult.setMetaDataList(metaDataList);
                indexMakerResult.setIndexList(indexList);

                if( explainResult.getSqlWithData()!=null && explainResult.getSqlWithData().equals("")){
                    explainResult.setSqlWithData(sql);
                }
                indexMakerResult.setExplainResult(explainResult);

                indexMakerResults.add(indexMakerResult);
            }
        }
        return indexMakerResults;
    }



}
