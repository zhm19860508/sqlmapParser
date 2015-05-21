package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;
import com.alibaba.druid.stat.TableStat;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-25
 * Time: 下午12:18
 * 禁止查询rule
 */
public class DenyRule extends AbstrctRule {


    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        Map<TableStat.Name, TableStat> tableMap = ruleParameter.getSqlMetaDataInfor().getTableStatMap();
        String sql = ruleParameter.getSqlString();

        checkResult.setAdvice("");
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
