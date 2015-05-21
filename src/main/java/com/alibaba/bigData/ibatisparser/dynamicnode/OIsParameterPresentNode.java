package com.alibaba.bigData.ibatisparser.dynamicnode;

import com.alibaba.bigData.ibatisparser.configuration.Commons;


/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-19
 * Time: 下午2:04
 * Ibatis的isparameterpresent
 */
public class OIsParameterPresentNode extends BaseIbatisNode {


    public String toString() {
        StringBuilder result = new StringBuilder();
        if (!propertyConfilct) {
            result.append(open);
            if (!removeFirstPrepend) {
                result.append(" ").append(prepend).append(" ");
            }
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
            result.append(open);
            if (!removeFirstPrepend) {
                result.append(" ").append(prepend).append(" ");
            }
            for (INodeElement sonNode : sonParseResult) {
                result.append(sonNode.toHtmlString());
            }
            result.append(" ").append(close).append(" ");
        }
        return result.toString();
    }

    public void setConfilct(String type) {
        if (type.equalsIgnoreCase(Commons.isnotparameterpresent)) {
            propertyConfilct = true;
        }
    }


}
