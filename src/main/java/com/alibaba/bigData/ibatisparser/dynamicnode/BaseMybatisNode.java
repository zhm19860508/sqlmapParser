package com.alibaba.bigData.ibatisparser.dynamicnode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoming.wm on 2014/8/20.
 * mybatis的base节点，mybatis的节点请extend此节点
 */
public class BaseMybatisNode implements INodeElement {


     String prefix="";

     String suffix="";

     String prefixOverrides="";

     String suffixOverrides="";


     String item="";

     String index="";

     String collection="";

     String open="";
     String close="";

     String separator="";


     String test="";

     boolean propertyConfilct=false;

     List<INodeElement> sonParseResult=new ArrayList<INodeElement>();


    @Override
    public String toHtmlString() {
        return "";
    }


    @Override
    public String toString() {
        return "";
    }

    @Override
    public void setPrepend(String prepend) {

    }

    @Override
    public void setOpen(String open) {
        this.open=open;
    }

    @Override
    public void setClose(String close) {
        this.close=close;
    }

    @Override
    public void setProperty(String property) {

    }

    @Override
    public void setRemoveFirstPrepend(boolean removeFirstPrepend) {

    }

    @Override
    public void setCompareProperty(String compareProperty) {

    }

    @Override
    public void setCompareValue(String compareValue) {

    }

    @Override
    public void setConjunction(String conjunction) {

    }

    @Override
    public void setConfilct(String type) {

    }

    @Override
    public void setSonParseResult(List<INodeElement> sonParseResult) {
        this.sonParseResult=sonParseResult;
    }

    @Override
    public void setTest(String test) {
        this.test=test;
    }

    @Override
    public void setPrefix(String prefix) {
        this.prefix=prefix;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix=suffix;
    }

    @Override
    public void setPrefixOverrides(String prefixOverrides) {
        this.prefixOverrides=prefixOverrides;
    }

    @Override
    public void setSuffixOverrides(String suffixOverrides) {
        this.suffixOverrides=suffixOverrides;
    }

    @Override
    public void setItem(String item) {
        this.item=item;
    }

    @Override
    public void setIndex(String index) {
        this.index=index;
    }

    @Override
    public void setCollection(String collection) {
        this.collection=collection;
    }

    @Override
    public void setSaparator(String saparator) {
        this.separator=saparator;
    }



}
