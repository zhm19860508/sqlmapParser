package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;
import com.alibaba.druid.stat.TableStat;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-3-13
 * Time: 上午11:48
 * Like查询rule
 */
public class LikeRule extends AbstrctRule {



    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);

        List<TableStat.Condition> conditionList = ruleParameter.getSqlMetaDataInfor().getConditionList();

        for (TableStat.Condition condition : conditionList) {
            if (condition.getOperator().toLowerCase().equals("like")) {
                checkResult.setSuccess(false);
                checkResult.setDetail(Commons.LIKE_RULE_WARING_MSG);
                checkResult.setAdvice(Commons.LIKE_RULE_DETAIL_MSG);
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
