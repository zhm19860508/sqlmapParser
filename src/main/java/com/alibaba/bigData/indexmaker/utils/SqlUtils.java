package com.alibaba.bigData.indexmaker.utils;

import com.alibaba.bigData.indexmaker.configuration.*;
import com.alibaba.bigData.indexmaker.objects.*;
import com.alibaba.bigData.indexmaker.sqlmeta.*;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-4-24
 * Time: 下午2:57
 * To change this template use File | Settings | File Templates.
 */
public class SqlUtils {

    /**
     * 处理sql中的变量
     */
    public static String handleSqlPara(SqlType sqlType, String sql) throws Exception {
        if (sqlType.equals(SqlType.MYSQL) && sql.toLowerCase().contains("limit")) {
            sql = fixMysqlLimitParam(sql);
        }
        if (sqlType.equals(SqlType.MYSQL) && !sql.trim().toLowerCase().startsWith("select")) {
            //处理非 select的语句
            DmlSQLParseUtils dml = new DmlSQLParseUtils(sql.trim().toLowerCase(), Commons.MYSQL_DB);
            sql = dml.getBackupSql().replaceAll("@@", "");
        }
        String paraStartChar;

        if (sqlType.equals(SqlType.MYSQL)) {
            paraStartChar = "@";
        } else if (sqlType.equals(SqlType.ORACLE)) {
            paraStartChar = ":";
        } else {
            paraStartChar = "#";
        }
        StringBuilder sb = new StringBuilder();
        boolean isParmClosed = true;

        for (int pos = 0; pos < sql.length(); pos++) {
            if (sql.substring(pos, pos + 1).equals("{")) {

                isParmClosed=false;
                if (sqlType.equals(SqlType.MYSQL)) {
                    sb.append("\"");
                } else {
                    sb.append(paraStartChar);
                }
            }else if(sql.substring(pos, pos + 1).equals("}")) {
                isParmClosed=true;
                if (sqlType.equals(SqlType.MYSQL)) {
                    sb.append("\"");
                }
            } else if (sql.substring(pos, pos + 1).equals(":")) {
                if (!isParmClosed) {
                    for (int i = 0; i < sql.substring(pos).indexOf("}"); i++) {
                        pos++;
                    }
                }
            }else if(sql.substring(pos, pos + 1).equals("#")){
                //donothin
            }else if(sql.substring(pos,pos+1).equals("$")){
                //donothin
            }else {
                sb.append(sql.substring(pos, pos + 1));
            }
        }

        return sb.toString();
    }

    /**
     * 修复mysql中带有limit的参数处理
     */
    public static String fixMysqlLimitParam(String sql) {
        Pattern pattern = Pattern.compile("limit\\s+(#\\s*\\{\\s*\\w+\\s*})\\s*(,\\s*\\s*\\{\\s*\\w+\\s*})*", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            if (matcher.group(1) != null) {
                String oldStr = matcher.group(1);
                String newStr = " 1 ";
                sql = sql.replace(oldStr, newStr);
            }
            if (matcher.group(2) != null) {
                String oldStr = matcher.group(2);
                String newStr = " ,10 ";
                sql = sql.replace(oldStr, newStr);
            }
        }
        return sql;
    }


    public static SqlExplainResult getSqlExpalin(Connection connection, String schName, String tableName, SqlType sqlType, String sql, SqlMetaDataInfor sqlMetaDataInfor) throws Exception {
        SqlExplainResult explainResult = new SqlExplainResult();

        if (sql.trim().toLowerCase().startsWith("select") || sql.trim().toLowerCase().startsWith("update") || sql.trim().toLowerCase().startsWith("delete")) {
            //处理变量
            String sqlWithPara = handleSqlPara(sqlType, sql);
            //mysql获取真实数据
            String sqlWithData = handleSqlData(sqlWithPara, schName, sqlType, connection, sqlMetaDataInfor);
            //获取sql的执行假话
            List<SqlExplain> sqlExplainResult = getExplianInfor(sqlWithData, sqlType, connection);
            explainResult.setSql(sql);
            explainResult.setSqlType(sqlType);
            explainResult.setSqlExplainResult(sqlExplainResult);
            explainResult.setSqlWithData(sqlWithData);
            explainResult.setSqlWithPara(sqlWithPara);

        }
        return explainResult;
    }

    public static List<SqlExplain> getExplianInfor(String sql, SqlType sqlType, Connection connection) throws SQLException {
        List<SqlExplain> result = new LinkedList<SqlExplain>();
        if (sqlType.equals(SqlType.MYSQL)) {
            String vsql = "explain " + sql;
            PreparedStatement preparedStatement = connection.prepareStatement(vsql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SqlMysqlExplain sqlExplainDO = new SqlMysqlExplain();
                sqlExplainDO.setId(resultSet.getString("id"));
                sqlExplainDO.setSelect_type(resultSet.getString("select_type"));
                sqlExplainDO.setTable(resultSet.getString("table"));
                sqlExplainDO.setType(resultSet.getString("type"));
                sqlExplainDO.setPossibleKey(resultSet.getString("possible_keys"));
                sqlExplainDO.setKey(resultSet.getString("key"));
                sqlExplainDO.setKeyLen(resultSet.getString("key_len"));
                sqlExplainDO.setRef(resultSet.getString("ref"));
                sqlExplainDO.setRows(resultSet.getString("rows"));
                sqlExplainDO.setExtra(resultSet.getString("extra"));
                //sqlExplainDO.setDbType(dbType);
                result.add(sqlExplainDO);
            }

        } else {
            String statementid = String.valueOf(System.currentTimeMillis());
            String vsql = "explain plan set statement_id='" + statementid + "' for " + sql;
            Statement preparedStatementPre = connection.createStatement();
            preparedStatementPre.execute(vsql);
            vsql = "SELECT id,replace(LPAD(' ', 4 * (LEVEL - 1)),' ','&nbsp;') || operation || ' ' || options operation,\n"
                    + "       optimizer,\n"
                    + "       decode(object_name,null,null,object_owner || '.' || object_name) object_name,\n"
                    + "       cost,\n"
                    + "       cardinality,\n"
                    + "       other_tag,\n"
                    + "       access_predicates,\n"
                    + "       filter_predicates,\n"
                    + "       bytes\n"
                    + "  FROM plan_table a\n"
                    + " START WITH id = 0\n"
                    + "        AND statement_id = ?\n"
                    + "CONNECT BY PRIOR id = parent_id\n"
                    + "       AND statement_id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(vsql);
            preparedStatement.setString((int)1, statementid);
            preparedStatement.setString((int)2, statementid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                SqlOracleExplain sqlExplainDO = new SqlOracleExplain();
                sqlExplainDO.setId(resultSet.getString("id"));
                sqlExplainDO.setOperation(resultSet.getString("operation"));
                sqlExplainDO.setOptimizer(resultSet.getString("optimizer"));
                sqlExplainDO.setObject_name(resultSet.getString("object_name"));
                sqlExplainDO.setCost(resultSet.getString("cost"));
                sqlExplainDO.setCardinality(resultSet.getString("cardinality"));
                sqlExplainDO.setOther_tag(resultSet.getString("other_tag"));
                sqlExplainDO.setAccess_predicates(resultSet.getString("access_predicates"));
                sqlExplainDO.setFilter_predicates(resultSet.getString("filter_predicates"));
                sqlExplainDO.setBytes(resultSet.getString("bytes"));
                //sqlExplainDO.setDbType(dbType);
                result.add(sqlExplainDO);
            }
            deleteSqlPlan(statementid, connection);
        }
        return result;
    }

    /**
     * 处理mysql中的数据
     */
    public static String handleSqlData(String sql, String dbName, SqlType sqlType, Connection connection, SqlMetaDataInfor sqlMetaDataInfor) throws Exception {
        if (sqlType.equals(SqlType.MYSQL)) {
            Map<TableStat.Name, TableStat> tables=sqlMetaDataInfor.getTableStatMap();
            List<TableStat.Condition> conditions=sqlMetaDataInfor.getConditionList();

            //依次处理多个表
            for (TableStat.Name table : tables.keySet()) {
                //Map<String, Object> tabResult = new HashMap<String, Object>();
                String tableName = table.getName();
                //如果是mysql,要先处理分表
                String realTableName = sqlMetaDataInfor.getRealTableNameMap().get(table);
                if (tableName != null && realTableName != null) {
                    sql = sql.replaceAll(tableName, realTableName);
                    //获取这个表中结构
                    String getTableStruSql = "select COLUMN_NAME,DATA_TYPE from information_schema.columns where TABLE_SCHEMA=? and table_name=?";
                    PreparedStatement statement = connection.prepareStatement(getTableStruSql);
                    String[] args = {dbName, realTableName};
                    statement.setString(1, args[0]);
                    statement.setString(2, args[1]);
                    ResultSet res = statement.executeQuery();
                    HashMap tableStrMap = new HashMap();
                    while (res.next()) {
                        tableStrMap.put(res.getString("COLUMN_NAME").toLowerCase(), res.getString("DATA_TYPE"));
                    }
                    //获取表的第一条数据
                    //  String getDataSql = "select * from " + dbName + "." + realTableName + " limit 1";
                    StringBuilder getDataSql = new StringBuilder();
                    getDataSql.append("select * from " + dbName + "." + realTableName);
                    for (TableStat.Condition condition : conditions) {
                        if (condition.getColumn().getTable().equals(realTableName)) {
                            String colName = condition.getColumn().getName().toLowerCase();
                            if (condition.getValues() != null && condition.getValues().size() > 0 && condition.getValues().get(0) != null) {
                                String preValue = condition.getValues().get(0).toString();
                                if (!sql.contains("\"" + preValue + "\"") && !preValue.matches(".*^(#|$|@|%).*")) {
                                    if (getDataSql.indexOf("where") == -1) {
                                        getDataSql.append(" where ");
                                        getDataSql.append(" " + colName+" " + condition.getOperator()+" " + preValue + " ");
                                    } else {
                                        getDataSql.append(" and " +" "+ colName + condition.getOperator()+" " + preValue + " ");
                                    }

                                }
                            }
                        }
                    }
                    getDataSql.append(" limit 1;");

                    statement = connection.prepareStatement(getDataSql.toString());
                    res = statement.executeQuery();
                    HashMap valueMap = new HashMap();
                    while (res.next()) {
                        for (Object columnName : tableStrMap.keySet()) {
                            valueMap.put(columnName.toString().toLowerCase(), res.getString((String) columnName));
                        }
                    }
                    res.close();
                    statement.close();
                    if (valueMap.size() < 1) {
                        throw new Exception(Commons.EXCPTION_HAS_NO_TEST_DATA);
                    }

                    SchemaStatVisitor visitor = DruidUtils.getSqlParserVisitor(sql, sqlType.toString().toLowerCase());
                    List<TableStat.Condition> conditionList = DruidUtils.getConditions(visitor);
                    //获取了表结构和数据结构tableStrMap,valueMap
                    for (TableStat.Condition condition : conditionList) {
                        if (condition.getColumn().getTable().equals(tableName)) {
                            String colName = condition.getColumn().getName().toLowerCase();
                            String colStrutur = (String) tableStrMap.get(condition.getColumn().getName().toLowerCase());
                            String colData = (String) valueMap.get(condition.getColumn().getName().toLowerCase());
                            if (colStrutur != null && condition.getValues() != null && condition.getValues().size() > 0 && condition.getValues().get(0) != null) {
                                if (colStrutur.contains("int") || colStrutur.contains("double") || colStrutur.contains("float") || colStrutur.contains("decimal") || colStrutur.contains("numeric")) {
                                    //数值类型
                                    String preValue = condition.getValues().get(0).toString();
                                    if (preValue != null && colData != null) {
                                        sql = sql.replaceAll("\"" + preValue + "\"", colData);

                                    }
                                } else if (colStrutur.contains("date") || colStrutur.contains("time")) {
                                    //时间类型
                                    String preValue = condition.getValues().get(0).toString();
                                    if (preValue != null) {
                                        sql = sql.replaceAll("\"" + preValue + "\"", " now() ");
                                    }
                                } else {
                                    String preValue = condition.getValues().get(0).toString();
                                    if (preValue != null && colData != null) {
                                        preValue = preValue.replaceAll("(%|\\$|@)", "");
                                        sql = sql.replaceAll("\"" + preValue + "\"", "\'" + colData + "\'");
                                    }
                                }
                            }
                        }
                    }

                } else {
                    throw new Exception("数据库中没有这个表或对应的分表:" + tableName);
                }
            }
        }
        return sql;
    }

    public static String getRealTableName(String tableName,SqlType sqlType,String schName,Connection connection) throws Exception {
        //取真是表名
        String realTableName=tableName;
        if (sqlType.equals(SqlType.MYSQL)) {
            realTableName = SqlUtils.getMysqlRealTableName(schName, tableName, connection);
        }
        return realTableName;
    }

    public static String getMysqlRealTableName(String schName, String tableName, Connection connection) throws Exception {
        PreparedStatement statement;
        String realTableName = null;

        //试着查找分表,优先找分表
        try {
            statement = connection.prepareStatement(SqlCommand.MYSQL_GET_REALTABLENAME);
            String[] args = {schName, tableName + "%"};
            statement.setString(1, args[0]);
            statement.setString(2, args[1]);
            ResultSet res = statement.executeQuery();
            Pattern pattern = Pattern.compile("^" + tableName.toLowerCase() + "_?[0-9]+$");
            while (res.next()) {
                realTableName = res.getString("table_name").toLowerCase();
                Matcher matcher = pattern.matcher(realTableName);
                if (matcher.find()) {
                    break;
                } else {
                    realTableName = null;
                }
            }
            res.close();
            statement.close();
        } catch (SQLException ignored) {
        }

        //通过分表规则找到了对应的表
        if (realTableName != null) {
            return realTableName;
        }

        //没有分表
        try {
            statement = connection.prepareStatement(SqlCommand.MYSQL_GET_REALTABLENAME);
            String[] args = {schName, tableName};
            statement.setString(1, args[0]);
            statement.setString(2, args[1]);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                realTableName = res.getString("table_name");
            }
            res.close();
            statement.close();
        } catch (SQLException ignored) {
        }
        //不是分表
        if (realTableName != null) {
            return realTableName;
        } else {
            //没有表名
            throw new Exception(Commons.EXCPTION_GET_NONE_TABLE);
        }
    }

    public static List<SqlInforIndex> getIndexInfor(String schName, String tableName, SqlType sqlType, Connection connection) throws Exception {
        List<SqlInforIndex> sqlInforIndexDOList = new ArrayList<SqlInforIndex>();
        if (sqlType.equals(SqlType.MYSQL)) {
            sqlInforIndexDOList = MetaDataUtils.getMysqlIndex(connection, schName, tableName);
        } else if (sqlType.equals(SqlType.ORACLE)) {
            sqlInforIndexDOList = MetaDataUtils.getOracleIndex(connection, schName, tableName);
        } else {
            throw new Exception(Commons.EXCPTION_UN_SUPPORT_SQLTYPE);
        }
        return sqlInforIndexDOList;
    }

    /**
     * 获取一个sql的表的字段信息
     */
    public static List<SqlInforColumn> getMetaData(String schName, SqlType sqlType, String tableName, Connection connection) throws Exception {
        List<SqlInforColumn> sqlInforColumnDOList = new ArrayList<SqlInforColumn>();
        //根据数据库信息和表名取表结构
        String commandForColumn;
        if (sqlType.equals(SqlType.MYSQL)) {
            sqlInforColumnDOList = MetaDataUtils.getMysqlMetaData(connection, schName, tableName);
        } else if (sqlType.equals(SqlType.ORACLE)) {
            sqlInforColumnDOList = MetaDataUtils.getOracleMetaData(connection, schName, tableName);
        } else {
            throw new Exception(Commons.EXCPTION_UN_SUPPORT_SQLTYPE);
        }
        return sqlInforColumnDOList;
    }

    /**
     * 获取一个表的行数
     */
    public static SqlTableInfo getTableInfo(String schName, SqlType sqlType, String tableName, Connection connection) throws Exception {
        SqlTableInfo sqlTableInfoDO = null;

        String commandForColumn;
        if (sqlType.equals(SqlType.MYSQL)) {
            sqlTableInfoDO = MetaDataUtils.getMysqlTabInfo(connection, schName, tableName);
        } else if (sqlType.equals(SqlType.ORACLE)) {

        } else {
            throw new Exception(Commons.EXCPTION_UN_SUPPORT_SQLTYPE);
        }
        return sqlTableInfoDO;
    }


    /**
     * 获取一个sql的表的字段信息
     */
    public static List<SqlInforColumn> getIndexData(String schName, SqlType sqlType, String tableName, Connection connection) throws Exception {
        List<SqlInforColumn> sqlInforColumnDOList = new ArrayList<SqlInforColumn>();
        //根据数据库信息和表名取表结构
        if (sqlType.equals(SqlType.MYSQL)) {
            sqlInforColumnDOList = MetaDataUtils.getMysqlMetaData(connection, schName, tableName);
        } else if (sqlType.equals(SqlType.ORACLE)) {
            sqlInforColumnDOList = MetaDataUtils.getOracleMetaData(connection, schName, tableName);
        } else {
            throw new Exception(Commons.EXCPTION_UN_SUPPORT_SQLTYPE);
        }
        return sqlInforColumnDOList;
    }




    public static int getIdxSize(String idxs, List<SqlInforColumn> metaDataList, SqlType sqlType) {
        int size = 0;
        if (idxs == null) {
            return 0;
        }
        String[] idxCols = idxs.split(",");
        if (sqlType.equals(SqlType.MYSQL)) {
            for (String col : idxCols) {
                int index = metaDataList.indexOf(col);
                if (index >= 0) {
                    SqlMysqlInfroColumn sqlmeta = (SqlMysqlInfroColumn) metaDataList.get(index);
                    size += ColTypeSize.getColTypeSize(sqlmeta.getColumnType().toLowerCase());
                }
            }
        }

        return size;
    }


    public static String getIndexCovered(List<SqlInforIndex> sqlInforIndexDOs, String columnName) {

        for (SqlInforIndex idx : sqlInforIndexDOs) {
            if (idx.getColumuName().replaceAll(",", "").toLowerCase().startsWith(columnName)) {
                return idx.getIndexName();
            }
        }
        return null;
    }

    public static String getIndexByName(List<SqlInforIndex> sqlInforIndexDOs, String indexName) {
        if (sqlInforIndexDOs == null) {
            return null;
        }
        for (SqlInforIndex idx : sqlInforIndexDOs) {
            if (idx.getIndexName().toLowerCase().equals(indexName.toLowerCase())) {
                return idx.getColumuName();
            }
        }
        return null;
    }

    public static Integer deleteSqlPlan(String iStatementId, Connection connection) {
        String vsql = "delete plan_table where statement_id=?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(vsql);
            preparedStatement.setString(1, iStatementId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return 0;
        }
    }
}
