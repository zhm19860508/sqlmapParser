package com.alibaba.bigData.indexmaker.objects;

import com.alibaba.bigData.indexmaker.configuration.SqlType;
import com.alibaba.bigData.indexmaker.sqlmeta.SqlExplain;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-12-30
 * Time: 下午6:35
 * 获取执行计划的结果
 */
public class SqlExplainResult {

    private String sql;

    private SqlType sqlType;

    private String sqlWithPara;

    private String sqlWithData;

    private List<SqlExplain> sqlExplainResult;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlWithPara() {
        return sqlWithPara;
    }

    public void setSqlWithPara(String sqlWithPara) {
        this.sqlWithPara = sqlWithPara;
    }

    public String getSqlWithData() {
        return sqlWithData;
    }

    public void setSqlWithData(String sqlWithData) {
        this.sqlWithData = sqlWithData;
    }

    public List<SqlExplain> getSqlExplainResult() {
        return sqlExplainResult;
    }

    public void setSqlExplainResult(List<SqlExplain> sqlExplainResult) {
        this.sqlExplainResult = sqlExplainResult;
    }
}
