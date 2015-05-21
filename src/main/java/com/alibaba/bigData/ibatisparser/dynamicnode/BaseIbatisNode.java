package com.alibaba.bigData.ibatisparser.dynamicnode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoming.wm on 2014/8/20.
 * 基本的ibatisnode
 */
public class BaseIbatisNode implements INodeElement {

    String prepend = "";

    String open = "";

    String close = "";

    String property;

    boolean removeFirstPrepend = false;
    String compareProperty;

    String compareValue;

    boolean propertyConfilct = false;

    String conjunction;

    List<INodeElement> sonParseResult = new ArrayList<INodeElement>();


    @Override
    public String toString() {
      return "";
    }

    @Override
    public String toHtmlString() {
        return "";
    }

    @Override
    public void setSonParseResult(List<INodeElement> sonParseResult) {
        this.sonParseResult = sonParseResult;
    }

    @Override
    public void setPrepend(String prepend) {
        this.prepend = prepend;
    }

    @Override
    public void setOpen(String open) {
        this.open = open;
    }

    @Override
    public void setClose(String close) {
        this.close = close;
    }

    @Override
    public void setProperty(String property) {
        this.property = property;
    }

    @Override
    public void setRemoveFirstPrepend(boolean removeFirstPrepend) {
        this.removeFirstPrepend = removeFirstPrepend;
    }

    @Override
    public void setCompareProperty(String compareProperty) {
        this.compareProperty = compareProperty;
    }

    @Override
    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    @Override
    public void setConjunction(String conjunction) {
        this.conjunction=conjunction;
    }

    @Override
    public void setTest(String test) {
    }

    @Override
    public void setPrefix(String prefix) {
    }

    @Override
    public void setSuffix(String suffix) {
    }

    @Override
    public void setPrefixOverrides(String prefixOverrides) {
    }

    @Override
    public void setSuffixOverrides(String suffixOverrides) {
    }

    @Override
    public void setItem(String item) {
    }

    @Override
    public void setIndex(String index) {
    }

    @Override
    public void setCollection(String collection) {
    }

    @Override
    public void setSaparator(String saparator) {
    }

    public void setConfilct(String type) {
    }

}
