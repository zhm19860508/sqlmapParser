package com.alibaba.bigData.ibatisparser.dynamicnode;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-19
 * Time: 下午2:07
 * ibatis的iterato节点
 */
public class IterateNode extends BaseIbatisNode {


    public String toString() {
        StringBuilder result = new StringBuilder();

        if (!removeFirstPrepend) {
            result.append(" ").append(prepend).append(" ");
        }
        result.append(" ").append(open).append(" ");
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toString());
        }
        result.append(" ").append(close).append(" ");
        String resultString = result.toString();
        if (resultString.contains("[")) {
            resultString = resultString.replaceAll("\\[", "");
        }

        if (resultString.contains("]")) {
            resultString = resultString.replaceAll("]", "");
        }
        return resultString;
    }

    public String toHtmlString() {
        StringBuilder result = new StringBuilder();

        if (!removeFirstPrepend) {
            result.append(" ").append(prepend).append(" ");
        }
        result.append(" ").append(open).append(" ");
        for (INodeElement sonNode : sonParseResult) {
            result.append(sonNode.toHtmlString());
        }
        result.append(" ").append(close).append(" ");
        String resultString = result.toString();
        if (resultString.contains("[")) {
            resultString = resultString.replaceAll("\\[", "");
        }

        if (resultString.contains("]")) {
            resultString = resultString.replaceAll("]", "");
        }
        return resultString;
    }


}
