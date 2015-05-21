package com.alibaba.bigData.ibatisparser.dynamicnode;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-18
 * Time: 下午11:08
 * XML节点类接口,其他node类都实现此接口
 */
public interface INodeElement {

    public String toString();

    public String toHtmlString();

    public void setPrepend(String prepend);

    public void setOpen(String open);

    public void setClose(String close);

    public void setProperty(String property);

    public void setRemoveFirstPrepend(boolean removeFirstPrepend);

    public void setCompareProperty(String compareProperty);

    public void setCompareValue(String compareValue);

    public void setConjunction(String conjunction);

    public void setConfilct(String type);

    public void setSonParseResult(List<INodeElement> sonParseResult);

    public void setTest(String test);

    public void setPrefix(String prefix);

    public void setSuffix(String suffix);

    public void setPrefixOverrides(String prefixOverrides);

    public void setSuffixOverrides(String suffixOverrides);

    public void setItem(String item);

    public void setIndex(String index);

    public void setCollection(String collection);

    public void setSaparator(String saparator);

}
