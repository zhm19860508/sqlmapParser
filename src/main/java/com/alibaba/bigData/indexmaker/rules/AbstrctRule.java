package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-5-7
 * Time: 下午4:17
 * Abstract RULE,所有的rule请继承
 */
public abstract class AbstrctRule {

    public String getRuleName() {
        return this.getClass().getSimpleName();
    }

    public abstract RuleCheckResult check(RuleCheckPara ruleParameter);

    abstract RuleCheckResult checkMysql(RuleCheckPara ruleParameter);

    abstract RuleCheckResult checkOracle(RuleCheckPara ruleParameter);

}
