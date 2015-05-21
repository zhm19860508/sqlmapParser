package com.alibaba.bigData.indexmaker.objects;

/**
 * Created by xiaoming.wm on 2014/5/12.
 * 外界idb的参数
 */
public class IdbTableInfor {

    private String tableName;

    private int tableSize;

    private String dbName;

    private int tableColNum;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getTableColNum() {
        return tableColNum;
    }

    public void setTableColNum(int tableColNum) {
        this.tableColNum = tableColNum;
    }
}
