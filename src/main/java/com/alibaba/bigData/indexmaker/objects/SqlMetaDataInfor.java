package com.alibaba.bigData.indexmaker.objects;

import com.alibaba.druid.stat.TableStat;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-18
 * Time: 下午3:03
 * sql语句解析语法树后的结果
 */
public class SqlMetaDataInfor {

    private Map<TableStat.Name, String> realTableNameMap;

    private Map<TableStat.Name, TableStat> tableStatMap;

    private List<TableStat.Condition> conditionList;

    private List<TableStat.Column> orderList;

    public Map<TableStat.Name, String> getRealTableNameMap() {
        return realTableNameMap;
    }

    public void setRealTableNameMap(Map<TableStat.Name, String> realTableNameMap) {
        this.realTableNameMap = realTableNameMap;
    }

    public Map<TableStat.Name, TableStat> getTableStatMap() {
        return tableStatMap;
    }

    public void setTableStatMap(Map<TableStat.Name, TableStat> tableStatMap) {
        this.tableStatMap = tableStatMap;
    }

    public List<TableStat.Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<TableStat.Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public List<TableStat.Column> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<TableStat.Column> orderList) {
        this.orderList = orderList;
    }
}
