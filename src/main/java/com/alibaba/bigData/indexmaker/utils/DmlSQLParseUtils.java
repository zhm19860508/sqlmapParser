package com.alibaba.bigData.indexmaker.utils;

import com.alibaba.bigData.indexmaker.configuration.Commons;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.ast.statement.SQLUpdateSetItem;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleDeleteStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleInsertStatement;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleSelectQueryBlock;
import com.alibaba.druid.sql.dialect.oracle.ast.stmt.OracleUpdateStatement;
import com.alibaba.druid.sql.dialect.oracle.parser.OracleStatementParser;
import com.alibaba.druid.sql.parser.SQLStatementParser;

import java.lang.Exception;import java.lang.RuntimeException;import java.lang.String;import java.lang.System;import java.util.ArrayList;
import java.util.List;

public class DmlSQLParseUtils {

    //update中错误的set语法检测正则表达式 set xx1=value1 and xx2=value2
    public final static String SET_ERROR_AND_VALUE = "[\\s\\S]*\\sand\\s[\\s\\S]*=[\\s\\S]*";
    String orgSql;
    String tableSource;// 不能使用tablename，因为需要别名，例：update t1 as a set a.name='dd'
    // where a.id=33253

    public String getSqlType() {
        return sqlType;
    }

    String sqlType;
    String whereSql;
    int dbType;
    String insertQueryCntSql;
    String limitSql;

    List<String> updateColumns = new ArrayList<String>();

    public List<String> getUpdateColumns() {
        return updateColumns;
    }

    List<String> tableNames = new ArrayList<String>();

    public List<String> getTableNames() {
        return tableNames;
    }

    int insertValuesCnt = 1;

    public DmlSQLParseUtils(String sql, int db_type) throws Exception {
        orgSql = sql;
        dbType = db_type;
        parse();
    }

    public void parse() throws Exception {
        SQLStatementParser parser;
        if (Commons.ORACLE_DB.equals(dbType)) {
            parser = new OracleStatementParser(orgSql);
            if (orgSql.toLowerCase().startsWith("update")) {
                sqlType = "update";
                OracleUpdateStatement statement = (OracleUpdateStatement) parser
                        .parseUpdateStatement();
                tableSource = SQLUtils.toOracleString(statement.getTableSource());
                buildTableNames(statement.getTableSource());
                buildUpdateColumnNames(statement);
                if (statement.getWhere() != null) {
                    whereSql = SQLUtils.toOracleString(statement.getWhere());
                } else {
                    throw new Exception("sql需要有where条件");
                }
            } else if (orgSql.toLowerCase().startsWith("delete")) {
                sqlType = "delete";
                OracleDeleteStatement statement = (OracleDeleteStatement) parser
                        .parseDeleteStatement();
                tableSource = SQLUtils.toOracleString(statement
                        .getTableSource());
                buildTableNames(statement.getTableSource());
                if (statement.getWhere() != null) {
                    whereSql = SQLUtils.toOracleString(statement.getWhere());
                } else {
                    throw new Exception("sql需要有where条件");
                }
            } else if (orgSql.toLowerCase().startsWith("insert")) {
                sqlType = "insert";
                OracleInsertStatement statement = (OracleInsertStatement) parser
                        .parseInsert();
                tableSource = SQLUtils.toOracleString(statement
                        .getTableSource());
                buildTableNames(statement.getTableSource());
                if (statement.getQuery() != null) {
                    sqlType = "insertselect";
                    ((OracleSelectQueryBlock) statement.getQuery().getQuery())
                            .getSelectList().clear();
                    String insertQuerySql = SQLUtils.toOracleString(statement
                            .getQuery());
                    insertQueryCntSql = "SELECT COUNT(*) cnt "
                            + insertQuerySql.substring(insertQuerySql
                            .indexOf("\nFROM"));
                }
            } else {
                throw new Exception("不支持的SQL类型，自助订正只支持INSERT,UPDATE,DELETE的SQL,请检查SQL语法是否正常,如果有其它语法SQL请选择操作类型为[申请DBA处理]");
            }
        } else if (Commons.MYSQL_DB.equals(dbType) || Commons.COBAR_DB.equals(dbType)) {
            parser = new MySqlStatementParser(orgSql);
            if (orgSql.toLowerCase().startsWith("update")) {
                sqlType = "update";
                MySqlUpdateStatement statement = (MySqlUpdateStatement) parser.parseUpdateStatement();
                tableSource = SQLUtils.toMySqlString(statement.getTableSource());
                buildTableNames(statement.getTableSource());
                buildUpdateColumnNames(statement);
                if (statement.getLimit() != null) {
                    limitSql =  SQLUtils.toMySqlString(statement.getLimit());
                }
                if (statement.getWhere() != null) {
                    whereSql = SQLUtils.toMySqlString(statement.getWhere());
                } else {
                    throw new Exception("sql需要有where条件");
                }
            } else if (orgSql.toLowerCase().startsWith("delete")) {
                sqlType = "delete";
                MySqlDeleteStatement statement = (MySqlDeleteStatement) parser
                        .parseDeleteStatement();
                if (statement.getTableSource() == null) {
                    throw new RuntimeException("has no delete table");
                }
                if (statement.getLimit() != null) {
                    limitSql =  SQLUtils.toMySqlString(statement.getLimit());
                }
                // SQLUtils.toMySqlString(statement.getTableSource().)
                tableSource = SQLUtils.toMySqlString(statement
                        .getTableSource());
                buildTableNames(statement.getTableSource());
                if (statement.getWhere() != null) {
                    whereSql = SQLUtils.toMySqlString(statement.getWhere());
                } else {
                    throw new Exception("sql需要有where条件");
                }
            } else if (orgSql.toLowerCase().startsWith("insert")) {
                sqlType = "insert";
                MySqlInsertStatement statement = (MySqlInsertStatement) parser
                        .parseInsert();
                insertValuesCnt = statement.getValuesList().size();

                tableSource = SQLUtils.toMySqlString(statement
                        .getTableSource());
                buildTableNames(statement.getTableSource());
                if (statement.getQuery() != null) {
                    sqlType = "insertselect";
                    MySqlSelectQueryBlock queryBlock = (MySqlSelectQueryBlock) statement.getQuery().getQuery();
                    if (queryBlock.getLimit() != null) {
                        insertQueryCntSql = "SELECT COUNT(*) cnt from ("
                                + SQLUtils.toMySqlString(statement
                                .getQuery()) + ") as t1";
                    } else {
                        queryBlock.getSelectList().clear();
                        String insertQuerySql = SQLUtils.toMySqlString(statement.getQuery());
                        insertQueryCntSql = "SELECT COUNT(*) cnt "
                                + insertQuerySql.substring(insertQuerySql
                                .indexOf("\nFROM"));
                    }
                }
            } else {
                throw new Exception("不支持的SQL类型,自助订正只支持INSERT,UPDATE,DELETE的SQL,请检查SQL语法是否正常,如果有其它语法SQL请选择操作类型为[申请DBA处理]");
            }
        } else {
            throw new Exception("无效的db_type");
        }
    }

    private void buildTableNames(SQLTableSource ts) {
        String tableExps[] = ts.toString().split(",");
        for (String name : tableExps) {
            String tableName = name.toLowerCase().replace("`", "").replaceAll("\"", "");
            int pos_dot = tableName.lastIndexOf(".");
            //db.table1,如果有表有库名前缀则去除前缀
            if (pos_dot > 0) {
                tableName = tableName.substring(pos_dot + 1);
            }
            tableNames.add(tableName);
        }
    }

    private void buildUpdateColumnNames(SQLUpdateStatement statement) {
        for (SQLUpdateSetItem item : statement.getItems()) {
            String columnName = item.getColumn().toString().toLowerCase().replace("`", "").replaceAll("\"", "");
            if (dbType != Commons.ORACLE_DB && (item.getValue() instanceof com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr) && item.getValue().toString().toLowerCase().matches(SET_ERROR_AND_VALUE)) {
                throw new RuntimeException("mysql数据库update解析发现语句中有[set 字段=值 and 字段=值]的可疑语法,不能自助订正正常写法是[update 字段=值,字段=值],请仔细确认\n如果确认没有问题请选择申请DBA处理");
            }
            int pos_dot = columnName.lastIndexOf(".");
            //t1.column1,如果列有表名前缀则去除前缀
            if (pos_dot > 0) {
                columnName = columnName.substring(pos_dot + 1);
            }
            updateColumns.add(columnName);
        }
        if (updateColumns.isEmpty()) {
            throw new RuntimeException("has no update column");
        }
    }

    public String getCntSql() throws Exception {
        if ("update".equals(sqlType) || "delete".equals(sqlType)) {
            if (limitSql!=null) {
                return "select count(*) cnt from (select 1 from " +  tableSource + " where " + whereSql +" "+ limitSql+") as t1";
            }else{
                if (whereSql != null) {
                    return "select count(*) cnt from " + tableSource + " where " + whereSql;
                } else {
                    return "select count(*) cnt from " + tableSource;
                }
            }
        } else if ("insert".equals(sqlType)) {
            throw new Exception("普通insert不支持转换为count的sql!");
        } else if ("insertselect".equals(sqlType)) {
            return insertQueryCntSql;
        } else {
            throw new Exception("获取count的SQL发现不支持的SQL类型! ");
        }
    }

    public String getBackupSql() throws Exception {
        if ("update".equals(sqlType) || "delete".equals(sqlType)) {
            if (whereSql != null) {
                return "select * from " + tableSource + " where " + whereSql;
            } else {
                return "select * from " + tableSource;
            }
        } else {
            throw new Exception("生成备份SQL发现不支持的SQL类型! ");
        }
    }

}
