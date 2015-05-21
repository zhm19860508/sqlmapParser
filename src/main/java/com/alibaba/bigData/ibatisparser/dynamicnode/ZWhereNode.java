package com.alibaba.bigData.ibatisparser.dynamicnode;

/**
 * Created by xiaoming.wm on 2014/5/19.
 * mybtais where节点
 */
public class ZWhereNode extends BaseMybatisNode {

    public String toString() {
        StringBuilder result = new StringBuilder();
        // FIXME: 这里少了一个where
        result.append(" where ");
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toString());
        }
        return result.toString();
    }

    public String toHtmlString() {
        StringBuilder result = new StringBuilder();
        result.append(" where ");
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toHtmlString());
        }
        return result.toString();
    }


}