package com.alibaba.bigData.ibatisparser.configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-18
 * Time: 下午4:30
 * To change this template use File | Settings | File Templates.
 * 一些常用的公共配置
 */
public class Commons {

    //sql类型
    public static List sqlTypeList = Arrays.asList("select", "insert", "delete", "update", "procedure");

    public static String SQLMAP = "SQLMAP";
    public static String MAPPER = "MAPPER";
    public static String SELECT = "SELECT";
    public static String UPDATE = "UPDATE";
    public static String INSERT = "INSERT";
    public static String DELETE = "DELETE";
    public static String SQL = "SQL";
    public static String TYPEALIAS = "TYPEALIAS";
    public static String RESULTMAP = "RESULTMAP";

    //字符表示
    public static String S_SPACE_STRING = " ";

    //出错的代码
    public static String E_PARSE_XML_FAIL = "E_PARSE_XML_FAIL";
    public static String E_SQLTYPE_NOT_SUPPORT = "E_SQLTYPE_NOT_SUPPORT";
    public static String E_NULL_SQL_ID = "E_NULL_SQL_ID";
    public static String E_PARSE_NODE_FAIL="E_PARSE_NODE_FAIL";
    public static String E_NO_INCLUDEID = "E_NO_INCLUDEID ID";

    //对照关系
    public static Map<String, String> nodeMap = new HashMap<String, String>() {
        {
            put("dynamic", "com.alibaba.bigData.ibatisparser.dynamicnode.DynamicNode");
            put("isempty", "com.alibaba.bigData.ibatisparser.dynamicnode.OIsEmptyNode");
            put("isequal", "com.alibaba.bigData.ibatisparser.dynamicnode.TIsEqualNode");
            put("isgreaterequal", "com.alibaba.bigData.ibatisparser.dynamicnode.TIsGreaterEqualNode");
            put("isgreaterthan", "com.alibaba.bigData.ibatisparser.dynamicnode.TIsGreaterThanNode");
            put("islessequal", "com.alibaba.bigData.ibatisparser.dynamicnode.TIsLessEqualNode");
            put("islessthan", "com.alibaba.bigData.ibatisparser.dynamicnode.TIsLessThanNode");
            put("isnotempty", "com.alibaba.bigData.ibatisparser.dynamicnode.OIsNotEmptyNode");
            put("isnotequal", "com.alibaba.bigData.ibatisparser.dynamicnode.TIsNotEqualNode");
            put("isnotnull", "com.alibaba.bigData.ibatisparser.dynamicnode.OIsNotNullNode");
            put("isnotpropertyavailable", "com.alibaba.bigData.ibatisparser.dynamicnode.OIsNotPropertyAvailableNode");
            put("isnull", "com.alibaba.bigData.ibatisparser.dynamicnode.OIsNullNode");
            put("isparameterpresent", "com.alibaba.bigData.ibatisparser.dynamicnode.OIsParameterPresentNode");
            put("isnotparameterpresent", "com.alibaba.bigData.ibatisparser.dynamicnode.OIsParameterPresentNode");
            put("ispropertyavailable", "com.alibaba.bigData.ibatisparser.dynamicnode.OIsPropertyAvailableNode");
            put("iterate", "com.alibaba.bigData.ibatisparser.dynamicnode.IterateNode");
            put("include", "com.alibaba.bigData.ibatisparser.dynamicnode.IncludeNode");
            put("selectkey", "");
        }
    };

    public static String dynamic = "dynamic";
    public static String isempty = "isempty";
    public static String isequal = "isequal";
    public static String isgreaterequal = "isgreaterequal";
    public static String isgreaterthan = "isgreaterthan";
    public static String islessequal = "islessequal";
    public static String islessthan = "islessthan";
    public static String isnotempty = "isnotempty";
    public static String isnotequal = "isnotequal";
    public static String isnotnull = "isnotnull";
    public static String isnotpropertyavailable = "isnotpropertyavailable";
    public static String isnull = "isnull";
    public static String isparameterpresent = "isparameterpresent";
    public static String isnotparameterpresent = "isnotparameterpresent";
    public static String ispropertyavailable = "ispropertyavailable";
    public static String iterate = "iterate";


    public static String ifnode = "if";
    public static String choose = "choose";
    public static String when = "when";
    public static String otherwise = "otherwise";
    public static String trim = "trim";
    public static String where = "where";
    public static String set = "set";
    public static String foreach = "foreach";
    public static String bind = "bind";
    //对照关系
    public static Map<String, String> mybatisNodeMap = new HashMap<String, String>() {
        {
            put("if", "com.alibaba.bigData.ibatisparser.dynamicnode.ZIfNode");
            put("choose", "com.alibaba.bigData.ibatisparser.dynamicnode.ZChooseNode");
            put("when", "com.alibaba.bigData.ibatisparser.dynamicnode.ZWhenNode");
            put("otherwise", "com.alibaba.bigData.ibatisparser.dynamicnode.ZOtherwiseNode");
            put("trim", "com.alibaba.bigData.ibatisparser.dynamicnode.ZTrimNode");
            put("where", "com.alibaba.bigData.ibatisparser.dynamicnode.ZWhereNode");
            put("set", "com.alibaba.bigData.ibatisparser.dynamicnode.ZSetNode");
            put("foreach", "com.alibaba.bigData.ibatisparser.dynamicnode.ZForeachNode");
            put("bind", "com.alibaba.bigData.ibatisparser.dynamicnode.ZBindNode");
            put("include", "com.alibaba.bigData.ibatisparser.dynamicnode.IncludeNode");
            put("selectkey", "");
        }
    };
}