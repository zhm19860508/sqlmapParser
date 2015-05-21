package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-3-13
 * Time: 上午11:08
 * 解析sql的rule
 */
public class ParseSqlRule extends AbstrctRule {


    public RuleCheckResult parserSqlCheck(Exception e) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        checkResult.setSuccess(false);
        checkResult.setDetail(Commons.PARSE_SQL_RULE_WARNING_MSG);
        checkResult.setAdvice(Commons.PARSE_SQL_RULE_DETAIL_MSG + e.getMessage());
        return checkResult;

    }

    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        checkResult.setSuccess(false);
        checkResult.setDetail(Commons.PARSE_SQL_RULE_WARNING_MSG);
        checkResult.setAdvice(Commons.PARSE_SQL_RULE_DETAIL_MSG);
        return checkResult;
    }

    @Override
    RuleCheckResult checkMysql(RuleCheckPara ruleParameter) {
        return null;
    }

    @Override
    RuleCheckResult checkOracle(RuleCheckPara ruleParameter) {
        return null;
    }


}
