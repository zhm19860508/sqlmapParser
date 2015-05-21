package com.alibaba.bigData.ibatisparser.dynamicnode;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-18
 * Time: 下午11:13
 * common的dynamic节点
 */
public class DynamicNode extends BaseCommonNode {

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(" ").append(prepend).append(" ");
        result.append(" ").append(open).append(" ");
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toString());
        }
        result.append(" ").append(close).append(" ");
        return result.toString();
    }

    public String toHtmlString() {
        StringBuilder result = new StringBuilder();
        result.append(" ").append(prepend).append(" ");
        result.append(" ").append(open).append(" ");
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toHtmlString());
        }
        result.append(" ").append(close).append(" ");
        return result.toString();
    }

}
