package com.alibaba.bigData.indexmaker.objects;

import java.util.LinkedList;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-12-20
 * Time: 上午7:40
 * 生成索引的结构
 */
public class IndexStruct {

    private String tableName;

    private LinkedList<String> columnName;

    //字段的权重
    private Map<String, Integer> columnWeight;

    public String getIndexString(){
        return "";
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LinkedList<String> getColumnName() {
        return columnName;
    }

    public void setColumnName(LinkedList<String> columnName) {
        this.columnName = columnName;
    }

    public Map<String, Integer> getColumnWeight() {
        return columnWeight;
    }

    public void setColumnWeight(Map<String, Integer> columnWeight) {
        this.columnWeight = columnWeight;
    }
}
