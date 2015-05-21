package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-6
 * Time: 下午4:48
 * 指定具体列名checkrule
 */
public class ColSpecifiedRule extends AbstrctRule {


    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        String sql = ruleParameter.getSqlString();
        StringBuilder sb = new StringBuilder(sql.toLowerCase().replaceAll("\\s*", ""));
        if (sb.toString().startsWith("insert")) {
            sb.delete(sb.indexOf("values"), sb.length());
            if ((sb.indexOf("(") == -1) || (sb.indexOf(")") == -1)) {
                checkResult.setSuccess(false);
                checkResult.setDetail(Commons.COLSPECIFILERULE_WARING_MSG);
                checkResult.setAdvice(Commons.COLSPECIFILERULE_DETAIL_MSG);
            }
        } else if (sb.toString().startsWith("select")) {
            if (sb.indexOf("*") != -1) {
                checkResult.setSuccess(false);
                checkResult.setDetail(Commons.COLSPECIFILERULE_WARING_MSG);
                checkResult.setAdvice(Commons.COLSPECIFILERULE_DETAIL_MSG);
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
