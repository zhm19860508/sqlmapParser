package com.alibaba.bigData.indexmaker.engine;

import com.alibaba.bigData.indexmaker.configuration.Configuration;
import com.alibaba.bigData.indexmaker.configuration.SqlType;
import com.alibaba.bigData.indexmaker.objects.*;
import com.alibaba.bigData.indexmaker.rules.*;
import com.alibaba.bigData.indexmaker.sqlmeta.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-3-12
 * Time: 下午5:50
 * sql规则引擎
 */
public class SqlCheckEngine {

    public static int MAX_TABLE_SIZE = 1024;
    public static int MAX_TABLE_ROWS = 100000;
    private String sqlString;
    private SqlType sqlType;
    Configuration configuration;
    private Connection connection;

    private String schName = "";

    //    private IndexMakerEngine idxMaker;
    private List<SqlInforColumn> metaDataList;
    private List<SqlInforIndex> indexList;
    private SqlExplainResult explainResult;
    private SqlMetaDataInfor sqlMetaDataInfor;
    private RuleCheckedDO ruleCheckedDO;
    private String isDynamicSql;
    private SqlTableInfo tabDO;
    private String filterFiles = "type,status,is_delete";

    public SqlCheckEngine(String schName, String sqlString, SqlType sqlType, Configuration configuration, SqlMetaDataInfor sqlMetaDataInfor, Connection connection, SqlExplainResult explainResult, List<SqlInforColumn> metaDataList, List<SqlInforIndex> indexList, SqlTableInfo tabDO, String isDynamicSql, RuleCheckedDO ruleCheckedDO) {
        this.sqlString = sqlString;
        this.sqlType = sqlType;
        this.configuration = configuration;
        this.sqlMetaDataInfor = sqlMetaDataInfor;
        this.explainResult = explainResult;
        this.connection = connection;
        this.schName = schName;
        this.isDynamicSql = isDynamicSql;
        this.ruleCheckedDO = ruleCheckedDO;
        this.tabDO = tabDO;
        this.indexList = indexList;
        this.metaDataList = metaDataList;
        this.ruleCheckedDO = ruleCheckedDO;
    }


    public List<RuleCheckResult> checkSql() throws Exception {
        List<RuleCheckResult> checkResultList = new ArrayList<RuleCheckResult>();
        RuleCheckPara ruleParameter = new RuleCheckPara(schName, sqlString, sqlType, sqlMetaDataInfor, connection, explainResult, metaDataList, indexList, tabDO, isDynamicSql);
        ruleParameter.setFilterFileds(filterFiles);
        // String ruleCheckConfig = ruleCheckedDO.getResult();

        String sql = ruleParameter.getSqlString();
        if (sql.trim().toLowerCase().startsWith("insert") || sql.trim().toLowerCase().startsWith("replace")) {
            return checkResultList;
        }

        //  DynamicSqlRule dynamicSqlRule=new DynamicSqlRule();
        if (configuration.shouldCheck(DynamicSqlRule.class.getSimpleName())) {
            DynamicSqlRule dynamicSqlRule = new DynamicSqlRule();
            RuleCheckResult c = dynamicSqlRule.check(ruleParameter);
            if (!c.isSuccess()) {
                checkResultList.add(c);
            }
        }

//            if(configuration.shouldCheck(WhereExistsRule.class.getSimpleName())){
//                WhereExistsRule whereExistsRule=new WhereExistsRule();
//                CheckResult c = whereExistsRule.check(ruleParameter);
//                if(!c.isSuccess()){
//                    checkResultList.add(c);
//                }
//            }

//        if(configuration.shouldCheck(AggregateRule.class.getSimpleName())){
//            AbstrctRule aggregateRule=new AggregateRule();
//            CheckResult c = aggregateRule.check(ruleParameter);
//            if(!c.isSuccess()){
//                checkResultList.add(c);
//            }
//        }


//        if(configuration.shouldCheck(ForceIndexRule.class.getSimpleName())){
//            ForceIndexRule forceIndexRule=new ForceIndexRule();
//            CheckResult c = forceIndexRule.check(ruleParameter);
//            if(c.isSuccess()){
//                checkResultList.add(c);
//            }
//        }
        if (configuration.shouldCheck(LikeRule.class.getSimpleName())) {
            LikeRule likeRule = new LikeRule();
            RuleCheckResult c = likeRule.check(ruleParameter);
            if (!c.isSuccess()) {
                checkResultList.add(c);
            }
        }


        if (configuration.shouldCheck(ConditionRule.class.getSimpleName())) {
            ConditionRule conditionRule = new ConditionRule();
            RuleCheckResult c = conditionRule.check(ruleParameter);
            if (!c.isSuccess()) {
                checkResultList.add(c);
            }
        }

//        if(configuration.shouldCheck(DenyRule.class.getSimpleName())){
//            DenyRule denyRule=new DenyRule();
//            CheckResult c = denyRule.check(ruleParameter);
//            if(!c.isSuccess()){
//                checkResultList.add(c);
//            }
//
//        }

        if (configuration.shouldCheck(MultiJoinRule.class.getSimpleName())) {
            MultiJoinRule multiJoinRule = new MultiJoinRule();
            RuleCheckResult c = multiJoinRule.check(ruleParameter);
            if (!c.isSuccess()) {
                checkResultList.add(c);
            }

        }

        //mysql执行计划check
        //加上where条件判断
        if (configuration.shouldCheck(WhereExistsRule.class.getSimpleName()) && configuration.shouldCheck(EmptyTableRule.class.getSimpleName())) {
            WhereExistsRule whereExistsRule = new WhereExistsRule();
            EmptyTableRule emptyTableRule = new EmptyTableRule();

            RuleCheckResult cWhere = whereExistsRule.check(ruleParameter);
            if (!cWhere.isSuccess()) {
                checkResultList.add(cWhere);
            }
            RuleCheckResult cEmpty = emptyTableRule.check(ruleParameter);
            if (!cEmpty.isSuccess()) {
                checkResultList.add(cEmpty);
            }

            if (sqlType.equals(SqlType.MYSQL) && cWhere.isSuccess() && cEmpty.isSuccess()) {
                if (configuration.shouldCheck(MysqlExplainRule.class.getSimpleName())) {
                    //存储merger表
                    MysqlExplainRule mysqlExplainRule = new MysqlExplainRule();
                    if (!mysqlExplainRule.check(ruleParameter).isSuccess()) {
                        checkResultList.add(mysqlExplainRule.check(ruleParameter));
//                            ruleParameter.getSqlMetaDataInfor().getTableStatMap() ;
//                            List<SqlInforIndexDO> indexList = getIndexInfor(schName, tableName);
//                            IndexMakerResult adviseIndex = idxMaker.getAdvIndex(conditionColumnList, indexList, tableName);
//                            List<TableStat.Column> orderByColumns = sqlMetaDataInfor.getOrderList();
//                            List<ConditionColumn> conditionColumnList = getConditionSet(conditions, orderByColumns, tableName);
//                            idxMaker = new  IndexMakerEngine(schName,sqlType,sqlString,connection);
//                            List<SqlInforColumnDO> metaDataList = SqlUtils.getMetaData(schName, sqlType, tableName, connection);
//                            try{
//                                IndexMakerResult adviseIndex = idxMaker.getAdvIndex(sqlMetaDataInfor,null,null);
//                            }catch(Exception e) {
//                                e.printStackTrace();
//                            }
                    }
                }
            }
            //oracle执行计划check
            if (sqlType.equals(SqlType.ORACLE) && !cWhere.isSuccess() && cEmpty.isSuccess()) {
                //存储merger表
                OracleExplainRule oracleExplainRule = new OracleExplainRule();
                if (!oracleExplainRule.check(ruleParameter).isSuccess()) {
                    checkResultList.add(oracleExplainRule.check(ruleParameter));
                }
            }
        }
        return checkResultList;

    }

    public SqlTableInfo getTabList() {
        return tabDO;
    }

    public void setTabList(SqlTableInfo tabDO) {
        this.tabDO = tabDO;
    }
}
