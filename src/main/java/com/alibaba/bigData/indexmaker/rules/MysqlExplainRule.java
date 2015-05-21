package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.configuration.SqlType;
import com.alibaba.bigData.indexmaker.engine.IndexMakerEngine;
import com.alibaba.bigData.indexmaker.objects.*;
import com.alibaba.bigData.indexmaker.sqlmeta.*;

import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-3-13
 * mysql执行计划
 */

public class MysqlExplainRule  extends AbstrctRule{
    //   public static String ruleName="MysqlExplainRule";


    public RuleCheckResult check(RuleCheckPara ruleParameter){
        RuleCheckResult checkResult=new RuleCheckResult(this.getRuleName(),true);
        SqlExplainResult explainResult=ruleParameter.getExplainResult();
        //获取执行计划
        if(explainResult.getSqlExplainResult()==null){
            return   checkResult;
        }
        String sql =  ruleParameter.getSqlString();
        if (sql.trim().toLowerCase().startsWith("insert") ||sql.trim().toLowerCase().startsWith("replace")) {
            return   checkResult;
        }
        List<SqlExplain> sqlExplainResults= explainResult.getSqlExplainResult();

        Iterator iter =  sqlExplainResults.iterator();
        while(iter.hasNext()){
            SqlMysqlExplain  sqlExplain=(SqlMysqlExplain) iter.next();
            //    sqlExplain.getTable().toLowerCase().contains("derived");
            if(sqlExplain.getKey()==null ){
                if(sqlExplain.getTable()==null){

                }else if(sqlExplain.getTable().toLowerCase().contains("derived")){

                }
                checkResult.setSuccess(false);
                checkResult.setDetail(Commons.MYSQL_EXPLAIN_USERKEY_NULL);
                String schName=ruleParameter.getSchName();
                SqlType sqlType=ruleParameter.getSqlType();
                String sqlString=ruleParameter.getSqlString();
                Connection connection=ruleParameter.getConnection();
                SqlMetaDataInfor sqlMetaDataInfor=ruleParameter.getSqlMetaDataInfor();
                List<SqlInforColumn> metaDataList=ruleParameter.getMetaDataList();
                List<SqlInforIndex> indexList=ruleParameter.getIndexList();
                IndexMakerEngine idxMaker= new IndexMakerEngine(schName,sqlType,sqlString,ruleParameter.getFilterFileds(),connection);
                //indexmake获取建议索引
                try {
                    //   IndexMakerResult adviseIndex = idxMaker.getAdvIndex(sqlMetaDataInfor,metaDataList,indexList,sqlExplainResults);
                    IndexAdviceResult adviseIndex = idxMaker.getAdvIndex(sqlMetaDataInfor,indexList,sqlExplainResults);
                    if(adviseIndex.getAdviceType()==Commons.HAS_PRIMARY_INDEX||adviseIndex.getAdviceType()==Commons.HAS_UNIQUE_INDEX||adviseIndex.getAdviceType()==Commons.HAS_COMMON_INDEX){
                        checkResult.setAdvice(Commons.MYSQL_EXIST_PRIMARYKEY_SOLUTION);
                    }else if(adviseIndex.getAdviceType()== Commons.GENERATE_INDEX_FAIL){
                        checkResult.setAdvice(Commons.MYSQL_EXPLAIN_MAKE_FAIL_SOLUTION);
                    }else if(adviseIndex.getAdviceType()== Commons.GENERATE_INDEX_FAIL_NO_COLUMN){
                        checkResult.setAdvice(Commons.MYSQL_NO_FILETER_COLUMN_SOLUTION);

                    }else {
                        checkResult.setAdvice(Commons.MYSQL_EXPLAIN_USERKEY_NULL_SOLUTION);
                        checkResult.setIndexMakerResult(adviseIndex);
                    }
                } catch (Exception e) {
                    checkResult.setAdvice(Commons.MYSQL_EXPLAIN_MAKE_FAIL_SOLUTION);
                }
                //   return   checkResult;
            }else{
                if(sqlExplain.getKey().toLowerCase().equals("primary")){
                    checkResult.setSuccess(true);
                    //    return  checkResult;
                }
                if(sqlExplain.getKey().toLowerCase().equals("normal")){
                    int keylen = Integer.parseInt( sqlExplain.getKeyLen());
                    int rows =   Integer.parseInt( sqlExplain.getRows());
                    checkResult.setSuccess(true);
                    //   return  checkResult;
                }
            }

        }
        return checkResult;
    }

    @Override
    RuleCheckResult checkMysql(RuleCheckPara ruleParameter) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    RuleCheckResult checkOracle(RuleCheckPara ruleParameter) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}