package com.alibaba.bigData.ibatisparser.utils;

import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.ParserException;

public class ZHMSQLUtil extends SQLUtils {

    public static String format(String sql, String dbType) {
        return format(sql, dbType, null);
    }

    public static String format(String sql, String dbType, List<Object> parameters) {
        try {
            List<SQLStatement> statementList = toStatementList(sql, dbType);

            return toSQLString(statementList, dbType, parameters);
        } catch (ParserException ex) {
            // LOG.warn("format error", ex);
            return sql;
        }
    }

}
