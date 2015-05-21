package com.alibaba.bigData.indexmaker.objects;

import com.alibaba.bigData.indexmaker.sqlmeta.SqlInforColumn;
import com.alibaba.bigData.indexmaker.sqlmeta.SqlInforIndex;

import java.util.List;

/**
 * Created by xiaoming.wm on 2014/8/21.
 * indexmaker的返回结果
 */
public class IndexMakerResult {

    private List<RuleCheckResult> sqlEngineResult;

    private String tableName;

    private String dbType;

    private List<SqlInforColumn> metaDataList;

    private List<SqlInforIndex> indexList;

    private SqlExplainResult explainResult;

    public SqlExplainResult getExplainResult() {
        return explainResult;
    }

    public void setExplainResult(SqlExplainResult explainResult) {
        this.explainResult = explainResult;
    }

    public List<SqlInforIndex> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<SqlInforIndex> indexList) {
        this.indexList = indexList;
    }

    public List<SqlInforColumn> getMetaDataList() {
        return metaDataList;
    }

    public void setMetaDataList(List<SqlInforColumn> metaDataList) {
        this.metaDataList = metaDataList;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<RuleCheckResult> getSqlEngineResult() {
        return sqlEngineResult;
    }

    public void setSqlEngineResult(List<RuleCheckResult> sqlEngineResult) {
        this.sqlEngineResult = sqlEngineResult;
    }
}
