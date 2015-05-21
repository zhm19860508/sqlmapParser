package com.alibaba.bigData.indexmaker.sqlmeta;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-8
 * Time: 上午11:10
 * sql的表结构体
 */
public class SqlTableInfo {

    String tableName;

    String schName;

    int tableTotSize;

    int dataSize;

    int idxSize;

    int dataRows;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public int getTableTotSize() {
        return tableTotSize;
    }

    public void setTableTotSize(int tableTotSize) {
        this.tableTotSize = tableTotSize;
    }

    public int getDataSize() {
        return dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public int getIdxSize() {
        return idxSize;
    }

    public void setIdxSize(int idxSize) {
        this.idxSize = idxSize;
    }

    public int getDataRows() {
        return dataRows;
    }

    public void setDataRows(int dataRows) {
        this.dataRows = dataRows;
    }
}
