package com.alibaba.bigData.ibatisparser.dynamicnode;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-18
 * Time: 下午11:13
 * command的include节点
 */
public class IncludeNode extends BaseCommonNode {


    public String toString() {
        StringBuilder result = new StringBuilder();

        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toString());
        }

        return result.toString();
    }

    public String toHtmlString() {
        StringBuilder result = new StringBuilder();

        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toHtmlString());
        }

        return result.toString();
    }

}
