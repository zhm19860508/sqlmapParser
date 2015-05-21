package com.alibaba.bigData.ibatisparser.dynamicnode;

/**
 * Created by xiaoming.wm on 2014/5/19.
 * mybatisTrimNode
 */
public class ZTrimNode extends BaseMybatisNode {


    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(" ").append(prefix).append(" ");
        result.append(" ").append(prefixOverrides).append(" ");
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toString());
        }
        result.append(" ").append(suffixOverrides).append(" ");
        result.append(" ").append(suffix).append(" ");
        return result.toString();
    }

    public String toHtmlString() {
        StringBuilder result = new StringBuilder();
        result.append(" ").append(prefix).append(" ");
        result.append(" ").append(prefixOverrides).append(" ");
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toHtmlString());
        }
        result.append(" ").append(suffixOverrides).append(" ");
        result.append(" ").append(suffix).append(" ");
        return result.toString();
    }

}