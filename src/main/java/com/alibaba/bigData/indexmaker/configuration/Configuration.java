package com.alibaba.bigData.indexmaker.configuration;


import com.alibaba.bigData.indexmaker.objects.RuleCheckedDO;
import com.alibaba.bigData.indexmaker.rules.AbstrctRule;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-12-6
 * Time: 下午3:32
 * 配置类
 */
public class Configuration {


    private RuleCheckedDO ruleCheckedDO;

    public Configuration(RuleCheckedDO ruleCheckedDO) {
        this.ruleCheckedDO = ruleCheckedDO;
    }


    public boolean shouldCheck(AbstrctRule aRule) {
        if (ruleCheckedDO != null && ruleCheckedDO.getResult().contains(aRule.getRuleName())) {
            return false;
        }
        return true;
    }

    public boolean shouldCheck(String ruleName) {
        if (ruleCheckedDO != null && ruleCheckedDO.getResult().contains(ruleName)) {
            return false;
        }
        return true;
    }

}
