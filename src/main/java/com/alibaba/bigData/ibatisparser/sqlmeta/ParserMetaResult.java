package com.alibaba.bigData.ibatisparser.sqlmeta;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-18
 * Time: 下午2:24
 * To change this template use File | Settings | File Templates.
 * 解析的结构化结果
 */
public class ParserMetaResult {

    //sqlId
    private String sqlId;

    //sql类型(insert,update,delete,select)
    private String sqlType;

    //是否是动态sql
    private boolean dynamicSql;

    //是否有includesql
    private boolean includeSql;

    //原始sql
    private String preSqlXml;

    //调整好的sql
    private String postSqlString;

    //sql元素据信息(表名，字段名)
    private SqlMetaDataInfor sqlMetaDataInfor;

    //动态字段的信息
    private DynamicMetaInfor dynamicMetaInfor;

    //namespace的信息

    private String nameSpace;

    private String xmlComment;

    private String formatSql;

    //ibtais还是mybatis
    private String xmlType;


    //getter and setter


    public String getXmlType() {
        return xmlType;
    }

    public void setXmlType(String xmlType) {
        this.xmlType = xmlType;
    }

    public boolean isIncludeSql() {
        return includeSql;
    }

    public void setIncludeSql(boolean includeSql) {
        this.includeSql = includeSql;
    }

    public String getFormatSql() {
        return formatSql;
    }

    public void setFormatSql(String formatSql) {
        this.formatSql = formatSql;
    }

    public String getXmlComment() {
        return xmlComment;
    }

    public void setXmlComment(String xmlComment) {
        this.xmlComment = xmlComment;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public boolean isDynamicSql() {
        return dynamicSql;
    }

    public void setDynamicSql(boolean dynamicSql) {
        this.dynamicSql = dynamicSql;
    }

    public String getPreSqlXml() {
        return preSqlXml;
    }

    public void setPreSqlXml(String preSqlXml) {
        this.preSqlXml = preSqlXml;
    }

    public String getPostSqlString() {
        return postSqlString;
    }

    public void setPostSqlString(String postSqlString) {
        this.postSqlString = postSqlString;
    }

    public SqlMetaDataInfor getSqlMetaDataInfor() {
        return sqlMetaDataInfor;
    }

    public void setSqlMetaDataInfor(SqlMetaDataInfor sqlMetaDataInfor) {
        this.sqlMetaDataInfor = sqlMetaDataInfor;
    }

    public DynamicMetaInfor getDynamicMetaInfor() {
        return dynamicMetaInfor;
    }

    public void setDynamicMetaInfor(DynamicMetaInfor dynamicMetaInfor) {
        this.dynamicMetaInfor = dynamicMetaInfor;
    }
}
