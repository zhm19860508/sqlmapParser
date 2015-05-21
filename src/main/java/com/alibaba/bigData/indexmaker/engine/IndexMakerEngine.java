package com.alibaba.bigData.indexmaker.engine;

import com.alibaba.bigData.indexmaker.configuration.*;
import com.alibaba.bigData.indexmaker.objects.*;
import com.alibaba.bigData.indexmaker.sqlmeta.*;
import com.alibaba.bigData.indexmaker.utils.MetaDataUtils;
import com.alibaba.bigData.indexmaker.utils.SqlUtils;
import com.alibaba.druid.stat.TableStat;

import java.sql.Connection;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-12-20
 * Time: 上午12:21
 * indexmaker引擎
 */
public class IndexMakerEngine {

    private String sqlString;

    private SqlType sqlType;

    Configuration configuration;

    private Connection connection;

    private String filterFileds;

    private String schName = "";

    /**
     * 初始化方法，传入要生成索引的sql及sql的相关类型
     *
     * @param sqlString
     * @param sqlType
     */
    public IndexMakerEngine(String schName, SqlType sqlType, String sqlString, String filterFileds, Connection connection) {
        this.sqlString = sqlString;
        this.sqlType = sqlType;
        this.connection = connection;
        this.schName = schName;
        this.filterFileds = filterFileds;
        // configuration=new Configuration();
    }

    public IndexAdviceResult getAdvIndex(SqlMetaDataInfor sqlMetaDataInfor, List<SqlInforIndex> indexList, List<SqlExplain> sqlExplainResult) throws Exception {
        IndexAdviceResult indexMakerResult = new IndexAdviceResult();
        Map<TableStat.Name, TableStat> tables = sqlMetaDataInfor.getTableStatMap();
        //获取条件set
        List<TableStat.Condition> conditions = sqlMetaDataInfor.getConditionList();
        //获取orderby条件
        List<TableStat.Column> orderByColumns = sqlMetaDataInfor.getOrderList();

        for (TableStat.Condition col : conditions) {
            for (SqlInforIndex idx : indexList) {
                if (idx.getColumuName().startsWith(col.getColumn().getName().toLowerCase())) {
                    if (idx.getIndexName().toLowerCase().equals("primary")) {
                        indexMakerResult.setAdviceType(Commons.HAS_PRIMARY_INDEX);
                        indexMakerResult.setIndexAdvice(Commons.HAS_PRIMARY_INDEX_TIP);
                        return indexMakerResult;
                    } else if (idx.getNonUnique().toLowerCase().equals("0")) {
                        indexMakerResult.setAdviceType(Commons.HAS_UNIQUE_INDEX);
                        indexMakerResult.setIndexAdvice(Commons.HAS_UNIQUE_INDEX_TIP);
                        return indexMakerResult;
                    }
                }
            }

        }
        for (TableStat.Name table : tables.keySet()) {
            Map<String, Object> tabResult = new HashMap<String, Object>();
            String tableName = table.getName();
            String realTableName = sqlMetaDataInfor.getRealTableNameMap().get(table);

            if (tableName != null && realTableName != null) {

                List<ConditionColumn> conditionColumnList = getConditionSet(conditions, orderByColumns, tableName);
                if (!(indexMakerResult.getAdviceType() > 0)) {
                    //调整条件的权重
                    conditionColumnList = sortCondionPriority(conditionColumnList, realTableName);
                    if (conditionColumnList != null && conditionColumnList.size() < 1) {
                        indexMakerResult.setAdviceType(Commons.GENERATE_INDEX_FAIL_NO_COLUMN);
                        indexMakerResult.setIndexAdvice(Commons.HAS_NO_FILTER_COLUMN_TIP);
                        return indexMakerResult;
                    }

                    try {
                        SqlIndexScriptResult sqlIndexScriptResult = generateIndexScript(conditionColumnList, tableName);

                        indexMakerResult.setAdviceType(Commons.HAS_GENERATE_INDEX);
                        indexMakerResult.setSqlIndexScriptResult(sqlIndexScriptResult);
                    } catch (Exception e) {
                        indexMakerResult.setAdviceType(Commons.GENERATE_INDEX_FAIL);
                        indexMakerResult.setSqlIndexScriptResult(new SqlIndexScriptResult(false));
                    }
                    List<SqlInforColumn> metaDataList = SqlUtils.getMetaData(schName, sqlType, tableName, connection);

                    int idxSize = SqlUtils.getIdxSize(indexMakerResult.getSqlIndexScriptResult().getColumnNames(), metaDataList, sqlType);
                    if (idxSize > ColTypeSize.MAX_IDX_SIZE) {
                        indexMakerResult.setIndexAdvice(tableName + Commons.HAS_GENERATE_INDEX_TIP);
                    } else {
                        indexMakerResult.setIndexAdvice(tableName + Commons.HAS_GENERATE_INDEX_TIP);
                    }
                    //indexMakerResult.setIndexAdvice(tableName + Commons.HAS_GENERATE_INDEX_TIP + indexAutoCreate);
                }

            }
        }

        return indexMakerResult;
    }

    /**
     * 根据list中的字段生成索引
     */
    private SqlIndexScriptResult generateIndexScript(List<ConditionColumn> conditionColumns, String tableName) throws Exception {
        SqlIndexScriptResult sqlIndexScriptResult = new SqlIndexScriptResult(true);
        Collections.sort(conditionColumns, new Comparator<ConditionColumn>() {
            @Override
            public int compare(ConditionColumn o1, ConditionColumn o2) {
                return (o2.getDistinctCount() - o1.getDistinctCount());
            }
        });

        if (conditionColumns != null && conditionColumns.size() > 0) {
            StringBuilder name = new StringBuilder();
            StringBuilder columns = new StringBuilder();
            for (int i = 0; i < conditionColumns.size() && i < 3; i++) {
                ConditionColumn conditionColumn = conditionColumns.get(i);
                if (conditionColumns.size() > 2) {
                    name.append(conditionColumn.getColumn().substring(0, 2)).append("_");
                } else {
                    name.append(conditionColumn.getColumn()).append("_");
                }
                columns.append(conditionColumn.getColumn()).append(",");
            }
            String indexName = "IDX_" + name.subSequence(0, name.length() - 1).toString();
            String columnName = columns.subSequence(0, columns.length() - 1).toString();
            String addScript = "ALTER TABLE " + tableName + " ADD INDEX " + indexName + " (" + columnName + ")";
            String dropScript = "ALTER TABLE " + tableName + " DROP INDEX " + indexName;
            sqlIndexScriptResult.setAddIndexScript(addScript);
            sqlIndexScriptResult.setDropIndexScript(dropScript);
            sqlIndexScriptResult.setIndexName(indexName);
            sqlIndexScriptResult.setColumnNames(columnName);
            sqlIndexScriptResult.setTableName(tableName);
            return sqlIndexScriptResult;
        } else {
            throw new Exception(Commons.EXCPTION_GENERATE_INDEX_FAIL);
        }
    }


    /**
     * 计算某一个表中的condtion中的各个字段的优先级
     */
    private List<ConditionColumn> sortCondionPriority(List<ConditionColumn> condtions, String tableName) throws Exception {
        List<ConditionColumn> sortConditonColumnList;
        if (sqlType.equals(SqlType.MYSQL)) {
            sortConditonColumnList = MetaDataUtils.getMysqlSortCondition(connection, condtions, tableName);
        } else if (sqlType.equals(SqlType.ORACLE)) {
            sortConditonColumnList = MetaDataUtils.getOracelSortCondition(connection, condtions, tableName);
        } else {
            throw new Exception(Commons.EXCPTION_UN_SUPPORT_SQLTYPE);
        }
        return sortConditonColumnList;
    }


    /**
     * 获取condition
     */
    private List<ConditionColumn> getConditionSet(List<TableStat.Condition> conditions, List<TableStat.Column> orders, String tableName) {
        List<ConditionColumn> conditionSetList = new ArrayList<ConditionColumn>();
        ConditionSet conditionSet = new ConditionSet();
        conditionSet.setConditions(conditions);
        conditionSet.setOrders(orders);
        conditionSet.setTableName(tableName);
        conditionSetList.addAll(conditionSet.getEqualContions());
        conditionSetList.addAll(conditionSet.getInContions());

        //conditionSetList.addAll(conditionSet.getOrdersColumns());
        return filter(conditionSetList, filterFileds);
    }

    private List<ConditionColumn> filter(List<ConditionColumn> conditions, String filterFileds) {
        String[] filter = filterFileds.split(",");
        List<ConditionColumn> conditionCols = new ArrayList<ConditionColumn>();
        conditionCols.addAll(conditions);
        for (ConditionColumn conditionColumn : conditions) {
            //System.out.println("##" + conditionColumn.getColumn());
            for (String f : filter) {
                if (conditionColumn.getColumn().toLowerCase().contains(f.toLowerCase())) {
                    conditionCols.remove(conditionColumn);

                }
            }
        }
        return conditionCols;
    }

}
