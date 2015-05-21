package com.alibaba.bigData.ibatisparser.objects;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-2-16
 * Time: 下午8:02
 * To change this template use File | Settings | File Templates.
 */
public class IncludeMapNode implements IXmlNodes {

    //Ibaits的classID
    private String sqlClassId;
    //sql的xml
    private String sqlXml;

    public String getSqlClassId() {
        return sqlClassId;
    }

    public void setSqlClassId(String sqlClassId) {
        this.sqlClassId = sqlClassId;
    }

    public String getSqlXml() {
        return sqlXml;
    }

    public void setSqlXml(String sqlXml) {
        this.sqlXml = sqlXml;
    }
}
