package com.alibaba.bigData.indexmaker.sqlmeta;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 12-6-29
 * Time: 下午6:44
 * oracle执行计划
 */
public class SqlOracleExplain implements SqlExplain {
    String id;
    String operation;
    String optimizer;
    String object_name;
    String cost;
    String cardinality;
    String other_tag;
    String access_predicates;
    String filter_predicates;
    String bytes;
    String dbType;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOptimizer() {
        return optimizer;
    }

    public void setOptimizer(String optimizer) {
        this.optimizer = optimizer;
    }

    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCardinality() {
        return cardinality;
    }

    public void setCardinality(String cardinality) {
        this.cardinality = cardinality;
    }

    public String getOther_tag() {
        return other_tag;
    }

    public void setOther_tag(String other_tag) {
        this.other_tag = other_tag;
    }

    public String getAccess_predicates() {
        return access_predicates;
    }

    public void setAccess_predicates(String access_predicates) {
        this.access_predicates = access_predicates;
    }

    public String getFilter_predicates() {
        return filter_predicates;
    }

    public void setFilter_predicates(String filter_predicates) {
        this.filter_predicates = filter_predicates;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }
}
