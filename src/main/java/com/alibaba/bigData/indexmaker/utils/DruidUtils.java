package com.alibaba.bigData.indexmaker.utils;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlSchemaStatVisitor;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.dialect.oracle.visitor.OracleSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-22
 * Time: 下午4:31
 * To change this template use File | Settings | File Templates.
 * Druid的相关操作类
 */
public class DruidUtils {

    /**
     * 获取SQLl的解析结果
     */
    public static SchemaStatVisitor getSqlParserVisitor(String sql, String dbType) {
        SchemaStatVisitor visitor;
        if ("ORACLE".equalsIgnoreCase(dbType)) {
            OracleStatementParser parser = new OracleStatementParser(sql);
            List<SQLStatement> statementList = parser.parseStatementList();
            SQLStatement statemen = statementList.get(0);
            visitor = new OracleSchemaStatVisitor();
            statemen.accept(visitor);
        } else {
            MySqlStatementParser parser = new MySqlStatementParser(sql);
            List<SQLStatement> statementList = parser.parseStatementList();
            SQLStatement statemen = statementList.get(0);
            visitor = new MySqlSchemaStatVisitor();
            statemen.accept(visitor);
        }
        return visitor;
    }

    /**
     * 获取表名
     *
     * @param visitor
     * @return
     */
    public static Map<TableStat.Name, TableStat> getTables(SchemaStatVisitor visitor) {
        Map<TableStat.Name, TableStat> tables = visitor.getTables();
        return tables;
    }

    /**
     * 获取条件字段
     *
     * @param visitor
     * @return
     */
    public static List<TableStat.Condition> getConditions(SchemaStatVisitor visitor) {
        List<TableStat.Condition> conditions = visitor.getConditions();
        return conditions;
    }

    /**
     * 获取条件字段
     *
     * @param visitor
     * @return
     */
    public static List<TableStat.Column> getOrders(SchemaStatVisitor visitor) {
        List<TableStat.Column> orderColumns = visitor.getOrderByColumns();
        return orderColumns;
    }

}
