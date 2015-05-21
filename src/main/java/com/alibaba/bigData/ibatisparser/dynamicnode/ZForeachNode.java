package com.alibaba.bigData.ibatisparser.dynamicnode;

/**
 * Created by xiaoming.wm on 2014/5/19.
 * mybatis foreach
 */
public class ZForeachNode extends BaseMybatisNode {


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(" ").append(open).append(" ");
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toString());
        }
        result.append(" ").append(close).append(" ");
        return result.toString();
    }

    @Override
    public String toHtmlString() {
        StringBuilder result = new StringBuilder();
        result.append(" ").append(open).append(" ");
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toHtmlString());
        }
        result.append(" ").append(close).append(" ");
        return result.toString();
    }
}