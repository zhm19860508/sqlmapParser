package com.alibaba.bigData.indexmaker.utils;


import com.alibaba.bigData.indexmaker.configuration.SqlCommand;
import com.alibaba.bigData.indexmaker.objects.ConditionColumn;
import com.alibaba.bigData.indexmaker.sqlmeta.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-12-27
 * Time: 上午11:19
 * 获取数据库元数据工具类
 */
public class MetaDataUtils {

    /**
     * 获取oracle的字典信息
     */
    public static List<SqlInforColumn> getMysqlMetaData(Connection connection, String schName, String tableName) throws Exception {
        List<SqlInforColumn> sqlInforColumnDOList = new ArrayList<SqlInforColumn>();
        PreparedStatement preparedStatementForCol = null;
        ResultSet resultSetForCol = null;
        try {
            preparedStatementForCol = connection.prepareStatement(SqlCommand.MYSQL_GET_METADATA);
            preparedStatementForCol.setString(1, schName);
            preparedStatementForCol.setString(2, tableName);
            resultSetForCol = preparedStatementForCol.executeQuery();
            while (resultSetForCol.next()) {
                SqlMysqlInfroColumn sqlInforColumnDO = new SqlMysqlInfroColumn();
                sqlInforColumnDO.setColumnType("MYSQL");
                sqlInforColumnDO.setTableSch(resultSetForCol.getString("table_schema"));
                sqlInforColumnDO.setTableName(resultSetForCol.getString("table_name"));
                sqlInforColumnDO.setColumnPostion(String.valueOf(resultSetForCol.getInt("ordinal_position")));
                sqlInforColumnDO.setColumnName(resultSetForCol.getString("column_name"));
                sqlInforColumnDO.setColumnDefault(resultSetForCol.getString("column_default"));
                sqlInforColumnDO.setColumnNullable(resultSetForCol.getString("is_nullable"));
                sqlInforColumnDO.setColumnType(resultSetForCol.getString("column_type"));
                sqlInforColumnDO.setColumnKey(resultSetForCol.getString("COLUMN_key"));
                sqlInforColumnDO.setColumnComment(resultSetForCol.getString("column_comment"));
                sqlInforColumnDOList.add(sqlInforColumnDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("");
        } finally {
            if (resultSetForCol != null) {
                resultSetForCol.close();
            }
            if (preparedStatementForCol != null) {
                preparedStatementForCol.close();
            }

        }
        return sqlInforColumnDOList;
    }


    /**
     * 获取oracle的字典信息
     */
    public static List<SqlInforColumn> getOracleMetaData(Connection connection, String schName, String tableName) throws Exception {
        List<SqlInforColumn> sqlInforColumnDOList = new ArrayList<SqlInforColumn>();
        PreparedStatement preparedStatementForCol = null;
        ResultSet resultSetForCol = null;
        try {
            preparedStatementForCol = connection.prepareStatement(SqlCommand.ORACLE_GET_METADATA);
            preparedStatementForCol.setString(1, schName.toUpperCase());
            preparedStatementForCol.setString(2, tableName.toUpperCase());
            resultSetForCol = preparedStatementForCol.executeQuery();
            while (resultSetForCol.next()) {
                SqlOracleInfroColumn sqlInforColumnDO = new SqlOracleInfroColumn();
                sqlInforColumnDO.setDbtype("ORACLE");
                sqlInforColumnDO.setTableSch(resultSetForCol.getString("table_schema"));
                sqlInforColumnDO.setTableName(resultSetForCol.getString("table_name"));
                sqlInforColumnDO.setColumnPostion(String.valueOf(resultSetForCol.getInt("column_id")));
                sqlInforColumnDO.setColumnName(resultSetForCol.getString("column_name"));
                sqlInforColumnDO.setColumnDefault(resultSetForCol.getString("data_default"));
                sqlInforColumnDO.setColumnNullable(resultSetForCol.getString("nullable"));
                sqlInforColumnDO.setColumnType(resultSetForCol.getString("data_type"));
                sqlInforColumnDO.setColumnLenght(String.valueOf(resultSetForCol.getInt("DATA_LENGTH")));
                sqlInforColumnDO.setColumnComment(resultSetForCol.getString("column_comment"));
                sqlInforColumnDOList.add(sqlInforColumnDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("get metadata fail");
        } finally {
            if (resultSetForCol != null) {
                resultSetForCol.close();
            }
            if (preparedStatementForCol != null) {
                preparedStatementForCol.close();
            }

        }
        return sqlInforColumnDOList;
    }

    /**
     * 获取mysql的索引信息
     */
    public static List<SqlInforIndex> getMysqlIndex(Connection connection, String schName, String tableName) throws Exception {
        List<SqlInforIndex> sqlInforIndexDOList = new ArrayList<SqlInforIndex>();
        PreparedStatement preparedStatementForIndex = null;
        ResultSet resultSetForIndex = null;
        try {
            preparedStatementForIndex = connection.prepareStatement(SqlCommand.MYSQL_GET_INDEX);
            preparedStatementForIndex.setString(1, schName);
            preparedStatementForIndex.setString(2, tableName);
            resultSetForIndex = preparedStatementForIndex.executeQuery();
            while (resultSetForIndex.next()) {
                SqlMysqlInforIndex sqlInforIndexDO = new SqlMysqlInforIndex();
                sqlInforIndexDO.setTableSch(resultSetForIndex.getString("table_schema"));
                sqlInforIndexDO.setTableName(resultSetForIndex.getString("table_name"));
                sqlInforIndexDO.setIndexName(resultSetForIndex.getString("index_name"));
                sqlInforIndexDO.setColumuName(resultSetForIndex.getString("COLUMN_NAME"));
                sqlInforIndexDO.setIndexType(resultSetForIndex.getString("INDEX_TYPE"));
                sqlInforIndexDO.setIndexComment(resultSetForIndex.getString("COMMENT"));
                sqlInforIndexDO.setCollation(resultSetForIndex.getString("Collation"));
                sqlInforIndexDO.setCardinality(resultSetForIndex.getString("Cardinality"));
                sqlInforIndexDO.setNonUnique(String.valueOf(resultSetForIndex.getInt("NON_UNIQUE")));
                sqlInforIndexDO.setSeqInIndex(String.valueOf(resultSetForIndex.getInt("Seq_in_index")));
                sqlInforIndexDO.setDbtype("MYSQL");
                sqlInforIndexDOList.add(sqlInforIndexDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("get index fail");
        } finally {
            if (resultSetForIndex != null) {
                resultSetForIndex.close();
            }
            if (preparedStatementForIndex != null) {
                preparedStatementForIndex.close();
            }

        }

        return sqlInforIndexDOList;
    }


    /**
     * 获取mysql的索引信息
     */
    public static List<SqlInforIndex> getOracleIndex(Connection connection, String schName, String tableName) throws Exception {
        List<SqlInforIndex> sqlInforIndexDOList = new ArrayList<SqlInforIndex>();
        PreparedStatement preparedStatementForIndex = null;
        ResultSet resultSetForIndex = null;
        try {
            preparedStatementForIndex = connection.prepareStatement(SqlCommand.ORACLE_GET_INDEX);
            preparedStatementForIndex.setString(1, schName.toUpperCase());
            preparedStatementForIndex.setString(2, tableName.toUpperCase());
            resultSetForIndex = preparedStatementForIndex.executeQuery();
            while (resultSetForIndex.next()) {
                SqlOracleInforIndex sqlInforIndexDO = new SqlOracleInforIndex();
                sqlInforIndexDO.setTableSch(resultSetForIndex.getString("table_schema"));
                sqlInforIndexDO.setTableName(resultSetForIndex.getString("table_name"));
                sqlInforIndexDO.setIndexName(resultSetForIndex.getString("index_name"));
                sqlInforIndexDO.setColumuName(resultSetForIndex.getString("COLUMN_NAME"));
                sqlInforIndexDO.setIndexType(resultSetForIndex.getString("INDEX_TYPE"));
                sqlInforIndexDO.setNonUnique(resultSetForIndex.getString("NON_UNIQUE"));
                sqlInforIndexDO.setPosition(resultSetForIndex.getString("COLUMN_POSITION"));
                sqlInforIndexDO.setDbtype("ORACLE");
                sqlInforIndexDOList.add(sqlInforIndexDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("get index fail");
        } finally {
            if (resultSetForIndex != null) {
                resultSetForIndex.close();
            }
            if (preparedStatementForIndex != null) {
                preparedStatementForIndex.close();
            }

        }
        return sqlInforIndexDOList;
    }

    /**
     * 获取mysql的表信息
     */
    public static SqlTableInfo getMysqlTabInfo(Connection connection, String schName, String tableName) throws Exception {
        SqlTableInfo sqlTableInfoDO = null;
        PreparedStatement preparedStatementForTable = null;
        ResultSet resultSetForTable = null;
        try {
            preparedStatementForTable = connection.prepareStatement(SqlCommand.MYSQL_GET_TABROWS);
            preparedStatementForTable.setString(1, tableName.toUpperCase());
            resultSetForTable = preparedStatementForTable.executeQuery();
            while (resultSetForTable.next()) {
                sqlTableInfoDO = new SqlTableInfo();
                sqlTableInfoDO.setSchName(schName);
                sqlTableInfoDO.setTableName(tableName);
                sqlTableInfoDO.setDataRows(resultSetForTable.getInt("TABLE_ROWS"));
                sqlTableInfoDO.setDataSize(resultSetForTable.getInt("DATA_LENGTH") / 1048576);
                sqlTableInfoDO.setIdxSize(resultSetForTable.getInt("INDEX_LENGTH") / 1048576);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("get index fail");
        } finally {
            if (resultSetForTable != null) {
                resultSetForTable.close();
            }
            if (preparedStatementForTable != null) {
                preparedStatementForTable.close();
            }

        }
        return sqlTableInfoDO;
    }

    /**
     * 获取mysql下的排序后字段
     */
    public static List<ConditionColumn> getMysqlSortCondition(Connection connection, List<ConditionColumn> condtions, String tableName) throws SQLException {
        if (condtions != null && condtions.size() > 0) {
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (ConditionColumn conditionColumn : condtions) {
                sb1.append("count(DISTINCT(").append(conditionColumn.getColumn()).append(") ) as ").append(conditionColumn.getColumn()).append(",");
                sb2.append(conditionColumn.getColumn()).append(",");
            }
            String command = "select " + sb1.subSequence(0, sb1.length() - 1).toString() + "  from (SELECT " + sb2.subSequence(0, sb2.length() - 1).toString() + " from " + tableName + " limit 100) t";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                for (ConditionColumn conditionColumn : condtions) {
                    int columnPriority = resultSet.getInt(conditionColumn.getColumn());
                    conditionColumn.setDistinctCount(columnPriority);
                }
            }
            resultSet.close();
            statement.close();

        }
        return condtions;
    }

    /**
     * 获取oracle下排序后字段
     */
    public static List<ConditionColumn> getOracelSortCondition(Connection connection, List<ConditionColumn> condtions, String tableName) throws SQLException {
        if (condtions != null && condtions.size() > 0) {
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            for (ConditionColumn conditionColumn : condtions) {
                sb1.append("count(DISTINCT(").append(conditionColumn.getColumn()).append(") ) as ").append(conditionColumn.getColumn()).append(",");
                sb2.append(conditionColumn.getColumn()).append(",");
            }
            String command = "select " + sb1.subSequence(0, sb1.length() - 1).toString() + "  from (SELECT " + sb2.subSequence(0, sb2.length() - 1).toString() + " from " + tableName + " where ROWNUM<100) t";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                for (ConditionColumn conditionColumn : condtions) {
                    int columnPriority = resultSet.getInt(conditionColumn.getColumn());
                    conditionColumn.setDistinctCount(columnPriority);
                }
            }
            resultSet.close();
            statement.close();
        }
        return condtions;
    }
}
