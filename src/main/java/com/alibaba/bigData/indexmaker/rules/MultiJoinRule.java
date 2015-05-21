package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;
import com.alibaba.druid.stat.TableStat;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-25
 * Time: 上午9:44
 * 多表查询检查rule
 */
public class MultiJoinRule extends AbstrctRule {



    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        Map<TableStat.Name, TableStat> tableMap = ruleParameter.getSqlMetaDataInfor().getTableStatMap();
        if (tableMap.size() > 2) {
            checkResult.setSuccess(false);
            checkResult.setDetail(Commons.HAS_MULTI_QUERY_WARNING_MSG);
            checkResult.setAdvice(Commons.HAS_MULTI_QUERY_DETAIL_MSG);
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
