package com.alibaba.bigData.indexmaker.objects;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-5-7
 * Time: 下午1:48
 * 已经检查过的RULE
 */
public class RuleCheckedDO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 具体的sql
     */
    private String sqldetail;

    /**
     * 检查结果
     */
    private String result;

    /**
     * 检查人
     */
    private String checker;

    /**
     * sqlid
     */
    private Long sqlId;

    /**
     * sqlmd5
     */
    private String md5sql;


    public Long getSqlId() {
        return sqlId;
    }

    public void setSqlId(Long sqlId) {
        this.sqlId = sqlId;
    }

    public String getMd5sql() {
        return md5sql;
    }

    public void setMd5sql(String md5sql) {
        this.md5sql = md5sql;
    }

    /**
     * setter for column 主键
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getSqldetail() {
        return sqldetail;
    }

    public void setSqldetail(String sqldetail) {
        this.sqldetail = sqldetail;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }
}
