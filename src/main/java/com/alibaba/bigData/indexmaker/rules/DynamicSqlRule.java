package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;

/**
 * Created by xiaoming.wm on 2014/5/6.
 * 动态sql检查rule
 */
public class DynamicSqlRule extends AbstrctRule {
    private static String IS_DYNAMICSQL = "true";
    private static String IS_NOT_DYNAMICSQL = "false";

    public RuleCheckResult check(RuleCheckPara ruleParameter) {

        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        if (!ruleParameter.getDanamicSql().equals(IS_NOT_DYNAMICSQL)) {

            String sqlXml = ruleParameter.getDanamicSql().toLowerCase();
            if (sqlXml.indexOf("dynamic") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("isempty") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("isequal") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("isgreaterequal") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("isgreaterthan") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("islessequal") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("islessthan") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("isnotempty") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("isnotequal") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("isnotnull") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("isnotpropertyavailable") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("isnull") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("isparameterpresent") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("ispropertyavailable") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("<if") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("<choose") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("<when") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("<otherwise") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("<trim") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("<where") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("<set") > sqlXml.indexOf("where")
                    || sqlXml.indexOf("<foreach") > sqlXml.indexOf("where")
                    ) {
                checkResult.setSuccess(false);
                checkResult.setDetail(Commons.DYNAMICSQL_DETAIL_MSG);
                checkResult.setAdvice(Commons.DYNAMICSQL_DETAIL_MSG);
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
