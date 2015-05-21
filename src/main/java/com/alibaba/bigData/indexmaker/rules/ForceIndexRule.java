package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-3-13
 * Time: 上午10:48
 * 强制索引检查rule
 */
public class ForceIndexRule extends AbstrctRule {


    static final String FORCE_INDEX_STRING = "forceindex";

    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        String sql = ruleParameter.getSqlString();
        if (sql.toLowerCase().replaceAll("\\s*", "").contains(FORCE_INDEX_STRING)) {
            checkResult.setSuccess(false);
            checkResult.setDetail(Commons.HAS_FORCE_WARING_MSG);
            checkResult.setAdvice(Commons.HAS_FORCE_DETAIL_MSG);
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
