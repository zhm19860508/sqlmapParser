package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-3-13
 * Time: 上午10:45
 * 聚合函数检查rule
 */
public class AggregateRule extends AbstrctRule {


    static final String MAX_FUNCTION = "max(";
    static final String MIN_FUNCTION = "min(";
    static final String SUM_FUNCITION = "sum(";
    static final String COUNT_FUNCTION = "count(";

    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        String sql = ruleParameter.getSqlString();
        if (sql.toLowerCase().replaceAll("\\s*", "").contains(COUNT_FUNCTION)) {
            checkResult.setSuccess(false);
            checkResult.setDetail(Commons.AGGREGATERULE_WARING_MSG);
            checkResult.setAdvice(Commons.AGGREGATERULE_DETAIL_MSG);
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
