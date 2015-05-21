package com.alibaba.bigData.ibatisparser.dynamicnode;

import com.alibaba.bigData.ibatisparser.configuration.Commons;


/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-19
 * Time: 上午11:50
 * ibatis的notproperytavailable节点
 */
public class OIsNotPropertyAvailableNode extends BaseIbatisNode {

    public String toString() {
        StringBuilder result = new StringBuilder();
        if (!propertyConfilct) {

            if (!removeFirstPrepend) {
                result.append(" ").append(prepend).append(" ");
            }
            result.append(" ").append(open).append(" ");
            for (INodeElement sonNode : sonParseResult) {
                result.append(sonNode.toString());
            }
            result.append(" ").append(close).append(" ");
        }
        return result.toString();
    }

    public String toHtmlString() {
        StringBuilder result = new StringBuilder();
        if (!propertyConfilct) {

            if (!removeFirstPrepend) {
                result.append(" ").append(prepend).append(" ");
            }
            result.append(" ").append(open).append(" ");
            for (INodeElement sonNode : sonParseResult) {
                result.append(sonNode.toHtmlString());
            }
            result.append(" ").append(close).append(" ");
        }
        return result.toString();
    }

    public void setConfilct(String type) {
        if (type.equalsIgnoreCase(Commons.ispropertyavailable)) {
            propertyConfilct = true;
        }
    }


}
