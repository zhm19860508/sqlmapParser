package com.alibaba.bigData.indexmaker.sqlmeta;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 12-6-28
 * Time: 下午3:09
 * index的结构体
 */
public interface SqlInforIndex {

    public String getIndexName();

    public String getNonUnique();

    public String getColumuName();
}
