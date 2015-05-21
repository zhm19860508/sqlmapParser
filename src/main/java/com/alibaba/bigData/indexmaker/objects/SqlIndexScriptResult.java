package com.alibaba.bigData.indexmaker.objects;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-1-16
 * Time: 下午12:19
 * To change this template use File | Settings | File Templates.
 */
public class SqlIndexScriptResult {

    private boolean success;

    private String addIndexScript;

    private String dropIndexScript;

    private String indexName;

    private String columnNames;

    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public SqlIndexScriptResult(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAddIndexScript() {
        return addIndexScript;
    }

    public void setAddIndexScript(String addIndexScript) {
        this.addIndexScript = addIndexScript;
    }

    public String getDropIndexScript() {
        return dropIndexScript;
    }

    public void setDropIndexScript(String dropIndexScript) {
        this.dropIndexScript = dropIndexScript;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(String columnNames) {
        this.columnNames = columnNames;
    }
}
