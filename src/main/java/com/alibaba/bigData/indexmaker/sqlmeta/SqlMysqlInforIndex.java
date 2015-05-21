package com.alibaba.bigData.indexmaker.sqlmeta;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 12-6-28
 * Time: 下午3:09
 * mysql结构体
 */
public class SqlMysqlInforIndex implements SqlInforIndex {

    private String tableSch;

    private String tableName;

    private String indexName;

    private String columuName;

    private String indexType;

    private String indexComment;

    private String nonUnique;

    private String seqInIndex;

    private String collation;

    private String cardinality;

    private String dbtype;


    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public String getNonUnique() {
        return nonUnique;
    }

    public void setNonUnique(String nonUnique) {
        this.nonUnique = nonUnique;
    }

    public String getSeqInIndex() {
        return seqInIndex;
    }

    public void setSeqInIndex(String seqInIndex) {
        this.seqInIndex = seqInIndex;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    public String getCardinality() {
        return cardinality;
    }

    public void setCardinality(String cardinality) {
        this.cardinality = cardinality;
    }

    public String getTableSch() {
        return tableSch;
    }

    public void setTableSch(String tableSch) {
        this.tableSch = tableSch;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getColumuName() {
        return columuName;
    }

    public void setColumuName(String columuName) {
        this.columuName = columuName;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getIndexComment() {
        return indexComment;
    }

    public void setIndexComment(String indexComment) {
        this.indexComment = indexComment;
    }
}
