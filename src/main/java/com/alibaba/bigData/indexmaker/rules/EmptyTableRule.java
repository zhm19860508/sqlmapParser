package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-6-3
 * Time: 下午7:54
 * 空表检查rule
 */
public class EmptyTableRule extends AbstrctRule {


    @Override
    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        if (ruleParameter.getExplainResult().getSqlWithData().equals("")) {
            checkResult.setSuccess(false);
            checkResult.setDetail(Commons.EMPTYTABLE_WARING_MSG);
            checkResult.setAdvice(Commons.EMPTYTABLE_DETAIL_MSG);
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
