package com.alibaba.bigData.indexmaker.objects;

import com.alibaba.bigData.indexmaker.configuration.SqlType;

import java.sql.Connection;

/**
 * Created by xiaoming.wm on 2014/5/12.
 * indexmaker传入参数
 */
public class IndexMakerPara {

    private String inputSql;

    private SqlType dbType;

    private String schName;

    private Connection dbConnection;

    private String dynamicSql;

    private RuleCheckedDO ruleCheckedDO;

    private IdbDbInfor idbDbInfroDO;



    public String getInputSql() {
        return inputSql;
    }

    public void setInputSql(String inputSql) {
        this.inputSql = inputSql;
    }

    public SqlType getDbType() {
        return dbType;
    }

    public void setDbType(SqlType dbType) {
        this.dbType = dbType;
    }

    public String getSchName() {
        return schName;
    }

    public void setSchName(String schName) {
        this.schName = schName;
    }

    public Connection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public String getDynamicSql() {
        return dynamicSql;
    }

    public void setDynamicSql(String dynamicSql) {
        this.dynamicSql = dynamicSql;
    }

    public RuleCheckedDO getRuleCheckedDO() {
        return ruleCheckedDO;
    }

    public void setRuleCheckedDO(RuleCheckedDO ruleCheckedDO) {
        this.ruleCheckedDO = ruleCheckedDO;
    }

    public IdbDbInfor getIdbDbInfroDO() {
        return idbDbInfroDO;
    }

    public void setIdbDbInfroDO(IdbDbInfor idbDbInfroDO) {
        this.idbDbInfroDO = idbDbInfroDO;
    }

}
