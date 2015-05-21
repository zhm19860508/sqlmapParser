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
 * Time: 上午11:08
 * oracle执行计划检查
 */
public class OracleExplainRule extends AbstrctRule {




    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        SqlExplainResult explainResult = ruleParameter.getExplainResult();
        List<SqlExplain> sqlExplainResults = explainResult.getSqlExplainResult();

        Iterator iter = sqlExplainResults.iterator();

        boolean hitIndex = false;
        while (iter.hasNext()) {
            SqlOracleExplain sqlExplain = (SqlOracleExplain) iter.next();
            if (sqlExplain.getOperation().toLowerCase().indexOf("index") > 0 && sqlExplain.getObject_name() != null ||
                    sqlExplain.getOperation().toLowerCase().equals("table access by rowid") ||
                    sqlExplain.getOperation().toLowerCase().equals("rowid lookup")) {
                //走到索引
                hitIndex = true;
                break;
            }
        }

        if (!hitIndex) {
            checkResult.setSuccess(false);
            checkResult.setDetail(Commons.ORACLE_EXPLAIN_USERKEY_NULL);
            String schName = ruleParameter.getSchName();
            SqlType sqlType = ruleParameter.getSqlType();
            String sqlString = ruleParameter.getSqlString();
            Connection connection = ruleParameter.getConnection();
            SqlMetaDataInfor sqlMetaDataInfor = ruleParameter.getSqlMetaDataInfor();
            List<SqlInforIndex> indexList = ruleParameter.getIndexList();
            IndexMakerEngine idxMaker = new IndexMakerEngine(schName, sqlType, sqlString, ruleParameter.getFilterFileds(), connection);
            //indexmake获取建议索引
            try {

                IndexAdviceResult adviseIndex = idxMaker.getAdvIndex(sqlMetaDataInfor, indexList, sqlExplainResults);
                if (adviseIndex.getAdviceType() == Commons.HAS_PRIMARY_INDEX || adviseIndex.getAdviceType() == Commons.HAS_UNIQUE_INDEX || adviseIndex.getAdviceType() == Commons.HAS_COMMON_INDEX) {
                    checkResult.setAdvice(Commons.ORACLE_EXIST_PRIMARYKEY_SOLUTION);
                } else if (adviseIndex.getAdviceType() == Commons.GENERATE_INDEX_FAIL) {
                    checkResult.setAdvice(Commons.ORACLE_EXPLAIN_MAKE_FAIL_SOLUTION);
                } else if (adviseIndex.getAdviceType() == Commons.GENERATE_INDEX_FAIL_NO_COLUMN) {
                    checkResult.setAdvice(Commons.ORACLE_NO_FILETER_COLUMN_SOLUTION);
                } else {
                    checkResult.setAdvice(Commons.ORACLE_EXPLAIN_USERKEY_NULL_SOLUTION);
                    checkResult.setIndexMakerResult(adviseIndex);
                }
            } catch (Exception e) {
                checkResult.setAdvice(Commons.ORACLE_EXPLAIN_MAKE_FAIL_SOLUTION);
            }
            return checkResult;
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
