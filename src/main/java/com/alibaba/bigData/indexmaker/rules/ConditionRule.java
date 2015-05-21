package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.SqlType;
import com.alibaba.bigData.indexmaker.engine.SqlCheckEngine;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;
import com.alibaba.bigData.indexmaker.sqlmeta.SqlInforIndex;
import com.alibaba.bigData.indexmaker.sqlmeta.SqlTableInfo;
import com.alibaba.bigData.indexmaker.utils.SqlUtils;
import com.alibaba.druid.stat.TableStat;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-9
 * Time: 下午7:43
 * 检查逻辑条件查询
 */
public class ConditionRule extends AbstrctRule {

    private static String ruleName = "ConditionRule";

    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = null;
        SqlType sqlType = ruleParameter.getSqlType();

        if (sqlType.equals(SqlType.MYSQL)) {
            checkResult = checkMysql(ruleParameter);
        } else if (sqlType.equals(SqlType.ORACLE)) {
            checkResult = checkOracle(ruleParameter);
        }
        return checkResult;
    }


    public RuleCheckResult checkMysql(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        List<SqlInforIndex> idxList = ruleParameter.getIndexList();
        SqlTableInfo tabInfo = ruleParameter.getSqlTableInfo();
        String priIdx = SqlUtils.getIndexByName(idxList, "primary");
        List<TableStat.Condition> conditionList = ruleParameter.getSqlMetaDataInfor().getConditionList();
        String sql = ruleParameter.getSqlString();
        StringBuilder sb = new StringBuilder();

        for (TableStat.Condition condition : conditionList) {
            if (condition.getOperator().replaceAll("\\s*", "").toLowerCase().equals("notin")) {
                if (!condition.getColumn().getName().equals(priIdx)) {
                    if (tabInfo.getDataSize() > SqlCheckEngine.MAX_TABLE_SIZE || tabInfo.getDataRows() > SqlCheckEngine.MAX_TABLE_ROWS) {
                        checkResult.setSuccess(false);
                        sb.append(sql.replace("not in", " in"));
                        sb.append("\n");
                    }
                }

            } else if (condition.getOperator().toLowerCase().equals("<>")) {
                if (!condition.getColumn().getName().equals(priIdx)) {
                    if (tabInfo.getDataSize() > SqlCheckEngine.MAX_TABLE_SIZE || tabInfo.getDataRows() > SqlCheckEngine.MAX_TABLE_ROWS) {
                        checkResult.setSuccess(false);
                        sb.append(sql.replace("<>", "="));
                        sb.append("\n");
                    }

                }
            } else if (condition.getOperator().toLowerCase().equals("!=")) {
                if (!condition.getColumn().getName().equals(priIdx)) {
                    if (tabInfo.getDataSize() > SqlCheckEngine.MAX_TABLE_SIZE || tabInfo.getDataRows() > SqlCheckEngine.MAX_TABLE_ROWS) {
                        checkResult.setSuccess(false);
                        sb.append(sql.replace("!=", "="));
                        sb.append("\n");
                    }
                } else if (condition.getOperator().replaceAll("\\s*", "").toLowerCase().equals("in")) {
                    if (SqlUtils.getIndexCovered(idxList, condition.getColumn().getName()) == null) {
                        checkResult.setSuccess(false);
                        sb.append(sql.replace("in", "="));
                        sb.append("\n");
                    }
                }

            }
        }
        checkResult.setAdvice(sb.toString());
        return checkResult;
    }

    public RuleCheckResult checkOracle(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(ruleName, true);
        return checkResult;
    }


}
