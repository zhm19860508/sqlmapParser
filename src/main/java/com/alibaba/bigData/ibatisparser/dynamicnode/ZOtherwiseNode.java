package com.alibaba.bigData.ibatisparser.dynamicnode;

import com.alibaba.bigData.ibatisparser.configuration.Commons;

/**
 * Created by xiaoming.wm on 2014/5/19.
 * mybatis的otherwiseNode
 */
public class ZOtherwiseNode extends BaseMybatisNode {


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

    public void setConfilct(String type) {
        if (type.equalsIgnoreCase(Commons.when)) {
            propertyConfilct = true;
        }
    }


}