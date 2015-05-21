package com.alibaba.bigData.indexmaker.objects;

import com.alibaba.bigData.indexmaker.configuration.SqlType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-12-20
 * Time: 上午12:30
 * indexMaker 返回结果
 */
public class IndexAdviceResult {

    private String sqlString;

    private SqlType sqlType;

    private Map<String,IndexStruct> indexStrutchs;

    private Map<String,String> indexSqlStrings;

    private SqlIndexScriptResult sqlIndexScriptResult;

    //1为有主键索引,2为有唯一键索引,3为有普通索引,9为自建索引
    private int adviceType;
    //索引意见
    private List<String> indexAdvice=new ArrayList<String>();

    //改写的索引意见
    public void setIndexAdvice(String indexAdvice) {
        if(indexAdvice!=null){
            this.indexAdvice.add(indexAdvice);
        }else {
            this.indexAdvice=new ArrayList<String>();
            this.indexAdvice.add(indexAdvice);
        }
    }

    public int getAdviceType() {
        return adviceType;
    }

    public void setAdviceType(int adviceType) {
        this.adviceType = adviceType;
    }

    public List<String> getIndexAdvice() {
        return indexAdvice;
    }

    public void setIndexAdvice(List<String> indexAdvice) {
        this.indexAdvice = indexAdvice;
    }

    public String getSqlString() {
        return sqlString;
    }

    public void setSqlString(String sqlString) {
        this.sqlString = sqlString;
    }

    public SqlType getSqlType() {
        return sqlType;
    }

    public void setSqlType(SqlType sqlType) {
        this.sqlType = sqlType;
    }

    public Map<String, IndexStruct> getIndexStrutchs() {
        return indexStrutchs;
    }

    public void setIndexStrutchs(Map<String, IndexStruct> indexStrutchs) {
        this.indexStrutchs = indexStrutchs;
    }

    public Map<String, String> getIndexSqlStrings() {
        return indexSqlStrings;
    }

    public void setIndexSqlStrings(Map<String, String> indexSqlStrings) {
        this.indexSqlStrings = indexSqlStrings;
    }

    public SqlIndexScriptResult getSqlIndexScriptResult() {
        return sqlIndexScriptResult;
    }

    public void setSqlIndexScriptResult(SqlIndexScriptResult sqlIndexScriptResult) {
        this.sqlIndexScriptResult = sqlIndexScriptResult;
    }
}
