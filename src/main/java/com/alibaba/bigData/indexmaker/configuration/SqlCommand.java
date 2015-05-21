package com.alibaba.bigData.indexmaker.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-12-27
 * Time: 上午12:48
 * 用于查询SQL字典的所有sql
 */
public class SqlCommand {

    //模糊查询某个表名，用来获取分表的真实表名
    public static final String MYSQL_GET_REALTABLENAME = "select table_name from information_schema.tables where table_schema=? and table_name like ? order by table_name";
    //获取oracle的元数据信息
    public static final String ORACLE_GET_METADATA = "SELECT col. OWNER table_schema, col.table_name, LOWER (col.column_name) column_name, col.column_id, col.data_default, col.nullable, col.data_type, COL.DATA_LENGTH, com.comments column_comment FROM dba_tab_columns col, dba_col_comments com WHERE COL.column_name = COM.column_name AND COL. OWNER = COM. OWNER AND COL.table_name = COM.table_name AND COL. OWNER = ? AND col.table_name =? ";
    //获取mysql的元数据信息
    public static final String MYSQL_GET_METADATA = "SELECT table_schema, table_name, column_name, ordinal_position, column_default, is_nullable, column_type, column_comment,COLUMN_key FROM information_schema.COLUMNS WHERE table_schema =? AND table_name = ?";
    //获取mysql索引信息
    public static final String MYSQL_GET_INDEX="SELECT TABLE_SCHEMA, table_name, INDEX_NAME, COLUMN_NAME, INDEX_TYPE, COMMENT,NON_UNIQUE,Seq_in_index,Collation,Cardinality FROM information_schema.statistics WHERE table_schema = ? AND table_name = ?";
    //获取mysql表行数
    public static final String MYSQL_GET_TABROWS=" SELECT TABLE_NAME,TABLE_ROWS,AVG_ROW_LENGTH,DATA_LENGTH,MAX_DATA_LENGTH,INDEX_LENGTH from information_schema.TABLES where TABLE_NAME=? ";
    //获取oracle索引信息
    public static final String ORACLE_GET_INDEX="SELECT ind. OWNER TABLE_SCHEMA, ind.table_name, LOWER (ind.index_name) index_name, COL.column_name COLUMN_NAME,COL.column_position COLUMN_POSITION, ind.index_type, ind.uniqueness NON_UNIQUE FROM dba_indexes ind, dba_ind_columns col WHERE ind.table_name = COL.table_name AND ind.index_name = COL.index_name AND ind. OWNER = ? AND ind.table_name = ? AND ind.index_name NOT LIKE 'SYS_IL%'";
}
