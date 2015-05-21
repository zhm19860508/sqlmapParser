package com.alibaba.bigData.indexmaker.rules;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.bigData.indexmaker.objects.RuleCheckPara;
import com.alibaba.bigData.indexmaker.objects.RuleCheckResult;
import com.alibaba.druid.stat.TableStat;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-3-12
 * Time: 下午6:19
 * 检查sql是否有where条件
 */
public class WhereExistsRule extends AbstrctRule {




    public RuleCheckResult check(RuleCheckPara ruleParameter) {
        RuleCheckResult checkResult = new RuleCheckResult(this.getRuleName(), true);
        List<TableStat.Condition> conditionList = ruleParameter.getSqlMetaDataInfor().getConditionList();
        Map<TableStat.Name, TableStat> tableMap = ruleParameter.getSqlMetaDataInfor().getTableStatMap();
        String sql = ruleParameter.getSqlString();
        if (tableMap.size() >= 1) {
            if ((tableMap.size() == 1) && (tableMap.containsKey("dual"))) {
                return checkResult;
            }
            if ((conditionList == null || conditionList.size() <= 0) || (!sql.toLowerCase().contains("where"))) {
                checkResult.setSuccess(false);
                checkResult.setDetail(Commons.HAS_NO_WHERE_WARNING_MSG);
                if (tableMap.size() == 1) {
                    TableStat table = tableMap.get(tableMap.keySet().iterator().next());
                    TableStat.Name tableName = tableMap.keySet().iterator().next();
                    StringBuffer sb = new StringBuffer();
                    sb.append(sql);
                    sb.append("t ,(SELECT id FROM ");
                    sb.append(tableName);
                    sb.append(" LIMIT a,b ) t1");
                    sb.append(" WHERE t.id=t1.id");
                    checkResult.setAdvice(sb.toString());
                }
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
