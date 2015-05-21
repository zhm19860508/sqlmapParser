package com.alibaba.bigData.ibatisparser.dynamicnode;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-19
 * Time: 下午3:42
 * common的cdata节点
 */
public class CdataNode extends BaseCommonNode {


    public String toString() {
        StringBuilder result = new StringBuilder();
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toString());
        }
        result.append(stringValue);
        return result.toString();
    }

    public String toHtmlString() {
        StringBuilder result = new StringBuilder();
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toHtmlString());
        }
        result.append(stringValue);
        return result.toString();
    }


}
