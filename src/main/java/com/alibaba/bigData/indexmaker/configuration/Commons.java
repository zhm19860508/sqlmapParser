package com.alibaba.bigData.indexmaker.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-12-6
 * Time: 下午3:33
 * 常用变量
 */
public class Commons {


    //字段的权重
    public static final int COLUMN_WEIGHT_MEDIA = 5;

    public static final int COLUMN_WEIGHT_HIGH = 9;

    public static final int COLUMN_WEIGHT_LOW = 1;

    //字段列的顺序
    public static final int COLUMN_POSITION_START = 1;

    //索引类型
    public static final int HAS_PRIMARY_INDEX = 1;
    public static final int HAS_UNIQUE_INDEX = 2;
    public static final int HAS_COMMON_INDEX = 3;
    public static final int HAS_GENERATE_INDEX = 9;
    public static final int GENERATE_INDEX_FAIL = 99;
    public static final int GENERATE_INDEX_FAIL_NO_COLUMN = 91;
    //索引提示


    public static final String HAS_PRIMARY_INDEX_TIP = " 已经存在主键索引 ";
    public static final String HAS_NO_FILTER_COLUMN_TIP = " 没有可以过滤的索引 ";
    public static final String HAS_UNIQUE_INDEX_TIP = " 已经存在唯一索引 ";
    public static final String HAS_COMMON_INDEX_TIP = " 已经存在索引 ";
    public static final String HAS_GENERATE_INDEX_TIP = " 生成一个索引 ";

    //数据库关键字
    public final static Integer ORACLE_DB = 0;
    public final static Integer MYSQL_DB = 1;
    public final static Integer OCEANBASE_DB = 2;
    public final static Integer HBASE_DB = 3;
    public final static Integer COBAR_DB = 10;

    public final static String ORACLE_DB_NAME = "oracle";
    public final static String MYSQL_DB_NAME = "mysql";
    public final static String COBAR_DB_NAME = "cobar";
    public final static String OCEANBASE_DB_NAME = "oceanbase";
    public final static String HBASE_DB_NAME = "hbase";

    public static String EXCPTION_CAN_NOT_GETCONNECTION="CAN_NOT_GETCONNECTION";
    public static String EXCPTION_UN_SUPPORT_SQLTYPE="UN_SUPPORT_SQLTYPE";

    public static String EXCPTION_GET_NONE_TABLE="GET_NONE_TABLE";
    public static String EXCPTION_GENERATE_INDEX_FAIL="GENERATE_INDEX_FAIL";

    public static String EXCPTION_HAS_NO_TEST_DATA="HAS_NO_TEST_DATA";


    public static String AGGREGATERULE_WARING_MSG = "该sql使用聚合函数";
    public static String AGGREGATERULE_DETAIL_MSG = "请确保聚合函数的性能";


    public static String COLSPECIFILERULE_WARING_MSG="SELECT或者INSERT没有指定具体的列";
    public static String COLSPECIFILERULE_DETAIL_MSG="请使用具体的列，不要使用select * 或者 insert into table values(col1,col2,……)";

    public static String DYNAMICSQL_WARING_MSG = "发现动态sql需要条件调整";
    public static String  DYNAMICSQL_DETAIL_MSG= "动态SQL请【双击】调整不同的sql";

    public static String EMPTYTABLE_WARING_MSG = "表内没有数据";
    public static String EMPTYTABLE_DETAIL_MSG = "请初始化数据，否则获取不到相应的执行计划和索引建议";

    public static String HAS_FORCE_WARING_MSG = "sql语句中包含FORCE INDEX";
    public static String HAS_FORCE_DETAIL_MSG = "sql的索引过滤性不好, 请确认force index是否合理";

    public static String LIKE_RULE_WARING_MSG = "sql语句中包含like查询";
    public static String LIKE_RULE_DETAIL_MSG = "请保证like查询使用了索引匹配";

    public static String HAS_NO_WHERE_CONDITON_WARNING_MSG = "没有检测到limit";
    public static String HAS_NO_WHERE_CONDITON_DETAIL_MSG = "请确认结果集大小，是否需要添加limit防止内存溢出";

    public static String HAS_MULTI_QUERY_WARNING_MSG = "sql包含多表查询";
    public static String HAS_MULTI_QUERY_DETAIL_MSG = "建议拆分";

    public static String MYSQL_EXPLAIN_USERKEY_NULL="sql没有使用索引 ";
    public static String MYSQL_EXPLAIN_USERKEY_NULL_SOLUTION = "请【双击】查看建议索引";
    public static String MYSQL_EXIST_PRIMARYKEY_SOLUTION = "该SQL存在索引，但是执行计划没有使用该索引，请联系DBA处理";
    public static String MYSQL_EXPLAIN_MAKE_FAIL_SOLUTION = "该SQL没有走到索引，获取自动索引失败，请联系DBA处理";
    public static String MYSQL_NO_FILETER_COLUMN_SOLUTION = "该SQL没有合适的索引过滤字段，获取自动索引失败，请改写SQL";

    public static String ORACLE_EXPLAIN_USERKEY_NULL="sql没有使用索引";
    public static String ORACLE_EXPLAIN_USERKEY_NULL_SOLUTION = "请【双击】查看建议索引";
    public static String ORACLE_EXIST_PRIMARYKEY_SOLUTION = "该SQL存在索引，但是执行计划没有使用该索引，请联系DBA处理";
    public static String ORACLE_EXPLAIN_MAKE_FAIL_SOLUTION = "该SQL没有走到索引，获取自动索引失败，请联系DBA处理";
    public static String ORACLE_NO_FILETER_COLUMN_SOLUTION = "该SQL没有合适的索引过滤字段，获取自动索引失败，请改写SQL";

    public static String HAS_ORDERBY_RAND_WARNING_MSG = "order by rand性能较差";
    public static String HAS_ORDERBY_RAND_DETAIL_MSG= "请不要使用order by rand()";


    public static String PARSE_SQL_RULE_WARNING_MSG = "sql在创建索引时出错";
    public static String PARSE_SQL_RULE_DETAIL_MSG = "错误信息";

    public static String MULTI_PRIKEY_WARING_MSG = "这个表使用非ID作为主键";
    public static String MULTI_PRIKEY_DETAIL_MSG = "请使用id作为主键";

    public static String HAS_NO_WHERE_WARNING_MSG = "没有检测到where条件";
    public static String HAS_NO_WHERE_DETAIL_MSG = "请保证有where提交进行匹配";

}
