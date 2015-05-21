package com.alibaba.bigData.indexmaker.objects;

import com.alibaba.bigData.indexmaker.configuration.SqlType;
import com.alibaba.bigData.indexmaker.sqlmeta.SqlInforColumn;
import com.alibaba.bigData.indexmaker.sqlmeta.SqlInforIndex;
import com.alibaba.bigData.indexmaker.sqlmeta.SqlTableInfo;

import java.sql.Connection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-3-13
 * Time: 下午6:13
 * rule校验的数据参数
 */
public class RuleCheckPara {

    private String sqlString;
    private SqlMetaDataInfor sqlMetaDataInfor;
    private SqlExplainResult explainResult;
    private SqlType sqlType;
    private Connection connection;
    private String danamicSql;
    private String schName = "";
    private List<SqlInforColumn> metaDataList;
    private List<SqlInforIndex> indexList;
    private SqlTableInfo tabDO;
    private String filterFileds;

    public RuleCheckPara(String schName, String sqlString, SqlType sqlType, SqlMetaDataInfor sqlMetaDataInfor, Connection connection, SqlExplainResult explainResult, List<SqlInforColumn> metaDataList, List<SqlInforIndex> indexList, SqlTableInfo tabDO, String isDanamicSql) {
        this.sqlString = sqlString;
        this.danamicSql = isDanamicSql;
        this.sqlMetaDataInfor = sqlMetaDataInfor;
        this.explainResult = explainResult;
        this.sqlType = sqlType;
        this.connection = connection;
        this.schName = schName;
        this.metaDataList = metaDataList;
        this.indexList = indexList;
        this.tabDO = tabDO;
    }

//    public String getIsDanamicSql() {
//        return danamicSql;
//    }
//
//    public void setIsDanamicSql(String isDanamicSql) {
//        this.danamicSql = isDanamicSql;
//    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public List<SqlInforColumn> getMetaDataList() {
        return metaDataList;
    }

    public void setMetaDataList(List<SqlInforColumn> metaDataList) {
        this.metaDataList = metaDataList;
    }

    public List<SqlInforIndex> getIndexList() {
        return indexList;
    }

    public void setIndexList(List<SqlInforIndex> indexList) {
        this.indexList = indexList;
    }

    public void setExplainResult(SqlExplainResult explainResult) {
        this.explainResult = explainResult;
    }

    public SqlExplainResult getExplainResult() {
        return explainResult;
    }

    public String getSqlString() {
        return sqlString;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }

    public SqlMetaDataInfor getSqlMetaDataInfor() {
        return sqlMetaDataInfor;
    }

    public void setSqlMetaDataInfor(SqlMetaDataInfor sqlMetaDataInfor) {
        this.sqlMetaDataInfor = sqlMetaDataInfor;
    }

    public String getDanamicSql() {
        return danamicSql;
    }

    public void setDanamicSql(String danamicSql) {
        this.danamicSql = danamicSql;
    }

    public SqlTableInfo getSqlTableInfo() {
        return tabDO;
    }

    public void setSqlTableInfo(SqlTableInfo tabDO) {
        this.tabDO = tabDO;
    }

    public String getFilterFileds() {
        return filterFileds;
    }

    public void setFilterFileds(String filterFileds) {
        this.filterFileds = filterFileds;
    }
}
