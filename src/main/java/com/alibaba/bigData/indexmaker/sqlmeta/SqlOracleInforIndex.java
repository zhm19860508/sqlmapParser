package com.alibaba.bigData.indexmaker.sqlmeta;


/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 12-6-28
 * Time: 下午3:09
 * oracle的infor结构体.
 */
public class SqlOracleInforIndex implements SqlInforIndex {

    private String tableSch;

    private String tableName;

    private String indexName;

    private String columuName;

    private String indexType;

    private String nonUnique;

    private String dbtype;

    private String position;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public String getNonUnique() {
        return nonUnique;
    }

    public void setNonUnique(String nonUnique) {
        this.nonUnique = nonUnique;
    }


    public String getTableSch() {
        return tableSch;
    }

    public void setTableSch(String tableSch) {
        this.tableSch = tableSch;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getColumuName() {
        return columuName;
    }

    public void setColumuName(String columuName) {
        this.columuName = columuName;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

}
