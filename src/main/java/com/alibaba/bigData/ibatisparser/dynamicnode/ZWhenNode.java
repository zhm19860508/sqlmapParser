package com.alibaba.bigData.ibatisparser.dynamicnode;


import com.alibaba.bigData.ibatisparser.configuration.Commons;

/**
 * Created by xiaoming.wm on 2014/5/19.
 * mybatisWhen节点
 */
public class ZWhenNode extends BaseMybatisNode {

    public String toString() {
        StringBuilder result = new StringBuilder();
        // FIXME: 这里解析mybatis的when节点的时候，多了一个where，不应该有这个where
        // result.append(" where ");
        if (!propertyConfilct) {
            for (INodeElement sonNode : sonParseResult) {
                result.append(sonNode.toString());
            }
        }
        return result.toString();
    }

    public String toHtmlString() {
        StringBuilder result = new StringBuilder();
        // result.append(" where ");
        if (!propertyConfilct) {
            for (INodeElement sonNode : sonParseResult) {
                result.append(sonNode.toHtmlString());
            }
        }
        return result.toString();
    }

    public void setConfilct(String type) {
        if (type.equalsIgnoreCase(Commons.otherwise)) {
            propertyConfilct = true;
        }
    }


}