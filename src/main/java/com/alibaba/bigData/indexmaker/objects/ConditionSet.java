package com.alibaba.bigData.indexmaker.objects;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.druid.stat.TableStat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-12-27
 * Time: 下午4:43
 * SQL语句中的条件字段的结果集
 */
public class ConditionSet {

    private String tableName;

    private List<TableStat.Condition> conditions;

    private List<TableStat.Column> orders;

    private Collection<ConditionColumn> ordersColumns;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableStat.Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<TableStat.Condition> conditions) {
        this.conditions = conditions;
    }


    public List<TableStat.Column> getOrders() {
        return orders;
    }

    public void setOrders(List<TableStat.Column> orders) {
        this.orders = orders;
    }

    /**
     * 获取操作符是等值的字段
     * @return
     */
    public List<ConditionColumn> getEqualContions(){
        List<ConditionColumn> conditionList=new ArrayList<ConditionColumn>();
        for (TableStat.Condition condition : conditions) {
            if (condition.getColumn().getTable().equalsIgnoreCase(tableName)&&(condition.getOperator().equals("="))) {
                ConditionColumn column=new ConditionColumn();
                column.setColumn(condition.getColumn().getName().toUpperCase());
                column.setWeight(Commons.COLUMN_WEIGHT_MEDIA);
                column.setPosition(Commons.COLUMN_POSITION_START);
                conditionList.add(column);
            }
        }
        return conditionList;
    }

    /**
     * 获取操作符是in的字段
     * @return
     */
    public List<ConditionColumn> getInContions(){
        List<ConditionColumn> conditionList=new ArrayList<ConditionColumn>();
        for (TableStat.Condition condition : conditions) {
            if (condition.getColumn().getTable().equalsIgnoreCase(tableName)&&condition.getOperator().equals("in")) {
                ConditionColumn column=new ConditionColumn();
                column.setColumn(condition.getColumn().getName().toUpperCase());
                column.setWeight(Commons.COLUMN_WEIGHT_MEDIA);
                column.setPosition(Commons.COLUMN_POSITION_START);
                conditionList.add(column);
            }
        }
        return conditionList;
    }

    /**
     * 获取排序字段
     */
    public Collection<ConditionColumn> getOrdersColumns() {
        List<ConditionColumn> orderColumns=new ArrayList<ConditionColumn>();
        if(orders!=null&&orders.size()>0){
            for (TableStat.Column order : orders) {
                if (order.getTable().equalsIgnoreCase(tableName)) {
                    ConditionColumn column=new ConditionColumn();
                    column.setColumn(order.getName().toUpperCase());
                    column.setWeight(Commons.COLUMN_WEIGHT_MEDIA);
                    column.setPosition(Commons.COLUMN_POSITION_START);
                    orderColumns.add(column);
                }
            }
        }
        return orderColumns;
    }
}
