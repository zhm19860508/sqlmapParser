package com.alibaba.bigData.ibatisparser.dynamicnode;

/**
 * Created by xiaoming.wm on 2014/5/19.
 * mybatis if节点
 */
public class ZIfNode extends BaseMybatisNode {

    public String toString() {
        StringBuilder result = new StringBuilder();
        if (!propertyConfilct) {
            for (INodeElement sonNode : sonParseResult) {
                result.append(sonNode.toString());
            }
        }
        return result.toString();
    }

    public String toHtmlString() {
        StringBuilder result = new StringBuilder();
        if (!propertyConfilct) {
            for (INodeElement sonNode : sonParseResult) {
                result.append(sonNode.toHtmlString());
            }
        }
        return result.toString();
    }

}