package com.alibaba.bigData.ibatisparser.dynamicnode;


/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-19
 * Time: 下午3:30
 * text node节点
 */
public class TextNode extends BaseCommonNode {

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toString());
        }
        result.append(textValue);
        return result.toString();
    }

    public String toHtmlString() {
        StringBuilder result = new StringBuilder();
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toHtmlString());
        }
        result.append(textValue);
        return result.toString();
    }


}
