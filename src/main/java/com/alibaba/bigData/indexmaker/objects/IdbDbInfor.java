package com.alibaba.bigData.indexmaker.objects;

import java.util.List;

/**
 * Created by xiaoming.wm on 2014/5/12.
 * 外界idb的参数
 */
public class IdbDbInfor {

    private String sql;

    private String dbType;

    private List<IdbTableInfor> idbTableInforDOList;

    private int dbid;

    private String dbName;


    public int getDbid() {
        return dbid;
    }

    public void setDbid(int dbid) {
        this.dbid = dbid;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public List<IdbTableInfor> getIdbTableInforDOList() {
        return idbTableInforDOList;
    }

    public void setIdbTableInforDOList(List<IdbTableInfor> idbTableInforDOList) {
        this.idbTableInforDOList = idbTableInforDOList;
    }
}
