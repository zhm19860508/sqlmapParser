package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-3-12
 * Time: 下午7:31
 * 检查是否有order by rand（）
 */
public class OrderbyRandRule extends AbstrctRule {


    static final String ORDERBY_RAND_STRING = "orderbyrand";

    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        String sql = ruleParameter.getSqlString();

        if (sql.toLowerCase().replaceAll("\\s*", "").contains(ORDERBY_RAND_STRING)) {
            checkResult.setSuccess(false);
            checkResult.setDetail(Commons.HAS_ORDERBY_RAND_WARNING_MSG);
            checkResult.setAdvice(Commons.HAS_ORDERBY_RAND_DETAIL_MSG);
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
