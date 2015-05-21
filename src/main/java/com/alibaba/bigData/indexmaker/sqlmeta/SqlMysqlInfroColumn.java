package com.alibaba.bigData.indexmaker.sqlmeta;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-1-14
 * Time: 下午5:16
 * Mysql的字段结构
 */
public class SqlMysqlInfroColumn implements SqlInforColumn {

    private String tableSch;

    private String tableName;

    private String columnName;

    private String columnPostion;

    private String columnDefault;

    private String columnNullable;

    private String columnType;

    private String columnKey;

    private String columnComment;

    private String dbtype;


    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
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

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnPostion() {
        return columnPostion;
    }

    public void setColumnPostion(String columnPostion) {
        this.columnPostion = columnPostion;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    public String getColumnNullable() {
        return columnNullable;
    }

    public void setColumnNullable(String columnNullable) {
        this.columnNullable = columnNullable;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }
}
