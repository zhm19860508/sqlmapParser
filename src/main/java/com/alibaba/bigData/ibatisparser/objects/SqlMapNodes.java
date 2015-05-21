package com.alibaba.bigData.ibatisparser.objects;

/**
 * Created with IntelliJ IDEA. User: xiaoming.wm Date: 14-2-14 Time: 下午6:46 To change this template use File | Settings
 * | File Templates.
 */
public class SqlMapNodes implements IXmlNodes, Cloneable {

    // Ibaits的classID
    private String sqlClassId;
    // ibatis的anmespace
    private String nameSpace;
    // sql的类型
    private String sqlType;
    // sql的注释
    private String sqlComments;
    // sql的xml
    private String sqlXml;
    // sql语句
    private String sqlQuery;
    // sql的格式化语句
    private String sqlFormatString;
    // sqlmap类型，ibatis or mybatis
    private String mapType;

    public Object clone() {
        try {
            return super.clone();

        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String getMapType() {
        return mapType;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }

    public String getSqlClassId() {
        return sqlClassId;
    }

    public void setSqlClassId(String sqlClassId) {
        this.sqlClassId = sqlClassId;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getSqlType() {
        return sqlType;
    }

    public void setSqlType(String sqlType) {
        this.sqlType = sqlType;
    }

    public String getSqlComments() {
        return sqlComments;
    }

    public void setSqlComments(String sqlComments) {
        this.sqlComments = sqlComments;
    }

    public String getSqlXml() {
        return sqlXml;
    }

    public void setSqlXml(String sqlXml) {
        this.sqlXml = sqlXml;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }

    public void setSqlQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public String getSqlFormatString() {
        return sqlFormatString;
    }

    public void setSqlFormatString(String sqlFormatString) {
        this.sqlFormatString = sqlFormatString;
    }
}
