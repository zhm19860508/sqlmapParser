package com.alibaba.bigData.ibatisparser.dynamicnode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoming.wm on 2014/8/20.
 * ibatis和mybatis公用节点
 */
public class BaseCommonNode implements INodeElement {



    String textValue;

    List<INodeElement> sonParseResult = new ArrayList<INodeElement>();

    String stringValue;


    String prepend = "";

    String open = "";

    String close = "";


    boolean removeFirstPrepend = false;
    String compareProperty;

    String compareValue;


    String conjunction;

    boolean propertyConfilct = false;

    String test = "";

    String prefix = "";

    String suffix = "";

    String prefixOverrides = "";

    String suffixOverrides = "";

    String item = "";

    String index = "";

    String collection = "";


    String separator = "";

    @Override
    public String toHtmlString() {
        return "";
    }

    @Override
    public void setProperty(String property) {

    }

    @Override
    public void setConfilct(String type) {

    }

    @Override
    public void setSaparator(String saparator) {

    }

    public String getTextValue() {
        return textValue;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public List<INodeElement> getSonParseResult() {
        return sonParseResult;
    }

    public void setSonParseResult(List<INodeElement> sonParseResult) {
        this.sonParseResult = sonParseResult;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getPrepend() {
        return prepend;
    }

    public void setPrepend(String prepend) {
        this.prepend = prepend;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public boolean isRemoveFirstPrepend() {
        return removeFirstPrepend;
    }

    public void setRemoveFirstPrepend(boolean removeFirstPrepend) {
        this.removeFirstPrepend = removeFirstPrepend;
    }

    public String getCompareProperty() {
        return compareProperty;
    }

    public void setCompareProperty(String compareProperty) {
        this.compareProperty = compareProperty;
    }

    public String getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    public String getConjunction() {
        return conjunction;
    }

    public void setConjunction(String conjunction) {
        this.conjunction = conjunction;
    }

    public boolean isPropertyConfilct() {
        return propertyConfilct;
    }

    public void setPropertyConfilct(boolean propertyConfilct) {
        this.propertyConfilct = propertyConfilct;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefixOverrides() {
        return prefixOverrides;
    }

    public void setPrefixOverrides(String prefixOverrides) {
        this.prefixOverrides = prefixOverrides;
    }

    public String getSuffixOverrides() {
        return suffixOverrides;
    }

    public void setSuffixOverrides(String suffixOverrides) {
        this.suffixOverrides = suffixOverrides;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
