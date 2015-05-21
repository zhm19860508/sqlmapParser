package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;
import com.alibaba.druid.stat.TableStat;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-23
 * Time: 上午9:17
 * limitcheck rule
 */
public class LimitExistsRule extends AbstrctRule {



    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        Map<TableStat.Name, TableStat> tableMap = ruleParameter.getSqlMetaDataInfor().getTableStatMap();
        String sql = ruleParameter.getSqlString();
        if (sql.toLowerCase().replaceAll("\\s*", "").contains("limit")) {
            checkResult.setSuccess(false);
            checkResult.setDetail(Commons.HAS_NO_WHERE_CONDITON_WARNING_MSG);
            checkResult.setAdvice(Commons.HAS_NO_WHERE_CONDITON_DETAIL_MSG);
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
