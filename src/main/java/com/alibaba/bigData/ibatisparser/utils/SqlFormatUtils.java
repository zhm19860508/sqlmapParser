package com.alibaba.bigData.ibatisparser.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 13-11-18
 * Time: 下午2:07
 * To change this template use File | Settings | File Templates.
 */
public class SqlFormatUtils {


    /**
     * 格式化sql，正则表达式，美化SQL
     */
    public static String formatTheSqlString(String unFormatSql) {
        //去除多余的空格回车换行符号
        String newSql = delSpace(unFormatSql);
        //修复##中有。?=的情形
        newSql = fixThePar(newSql);
        //去除多余的空格
        newSql = delByPattern(newSql);
        //修复有#=的问题
        newSql = fixTheVar(newSql);
        //有where 后有，and，or的情形
        newSql = fixPrependWhere(newSql);
        //修复括号的问题
        newSql = fixTheBracket(newSql);
        //修复_空格的问题
        newSql = fixLineBlank(newSql);
        //修复#中有comment关键字# ,去掉start和end的关键字
        newSql = fixKeyWord(newSql);
        //空变量的情形
        newSql = fixNonPar(newSql);
        return newSql;
    }


    /**
     * 去除多余的回车换行空格符
     */
    public static String delSpace(String sql) {
        return sql.trim().replace("\n", " ").replace("\t", " ").replace("\r", " ").replace("\f", " ");
    }

    /**
     * 修复sql的变量中有，?=,:的情形
     */
    public static String fixThePar(String newSql) {
        Pattern pattern = Pattern.compile("#(\\w+(\\w*[,|=|.|:]\\w*)+\\w+)#", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(newSql);
        while (matcher.find()) {
            String oldStr = matcher.group(1);
            String newStr = matcher.group(1).replaceAll(",", "_").replaceAll("=", "_").replaceAll("\\.", "_").replaceAll(":", "_");
            newSql = newSql.replace(oldStr, newStr);
        }
        return newSql;
    }

    /**
     * 合并多余的空格
     */
    public static String delByPattern(String sql) {
        Pattern p = Pattern.compile(" {2,}");
        Matcher m = p.matcher(sql);
        return m.replaceAll(" ");
    }

    /**
     * 修复#=的问题
     */
    public static String fixTheVar(String sql) {
        if (sql.indexOf("=#") > 0) {
            return sql.replaceAll("=#", "= #");
        }
        return sql;
    }


    /**
     * 修复prepend的问题
     */
    public static String fixPrependWhere(String str) {
        if (str.toLowerCase().contains(" where ,") || str.toLowerCase().contains(" where and ") || str.toLowerCase().contains(" where or ")) {
            str = str.toLowerCase().replaceAll(" where\\s(,|and|or)\\s", " where ");
        }
        //修复有set ，的情形
        if (str.toLowerCase().contains(" set ,")) {
            str = str.toLowerCase().replaceAll(" set\\s,", " set ");
        }
        //修复，，的问题
        if (str.contains(", ,")) {
            str = str.toLowerCase().replaceAll(",\\s,", " , ");
        }
        //修复And and的问题
        if (str.toLowerCase().contains(" and and")) {
            str = str.toLowerCase().replaceAll(" and\\sand\\s", " and ");
        }
        //修复位与的问题dbaSvnFileId=703
        if (str.contains("&#")) {
            str = str.replaceAll("&#", "& #");
        }
        //修复！=-1的问题
        if (str.contains("!=-")) {
            str = str.replaceAll("!=-", "!= -");
        }
        //修复, where的问题
        if (str.contains(", where")) {
            str = str.toLowerCase().replaceAll(",\\swhere\\s", " where ");
        }
        //修复, where的问题
        if (str.contains(" where ,")) {
            str = str.toLowerCase().replaceAll(" where\\s,", " where ");
        }
        //修复, where的问题
        if (str.contains(", values")) {
            str = str.toLowerCase().replaceAll(",\\svalues\\s", " values ");
        }

        //修复, where的问题
        if (str.contains(" values ,")) {
            str = str.toLowerCase().replaceAll(" values\\s,", " values ");
        }

        //修复, where的问题
        if (str.contains(", )")) {
            str = str.toLowerCase().replaceAll(",\\s\\)", " ) ");
        }
        //修复, where的问题
        if (str.contains(", (")) {
            str = str.toLowerCase().replaceAll(",\\s\\(", " ( ");
        }
        return str;
    }


    /**
     * 修复[]的问题
     */
    public static String fixTheBracket(String str) {
        if (str.indexOf("[]") > 0) {
            return str.replaceAll("\\[]", "");
        }
        return str;
    }

    /**
     * 修复_ XXX的问题
     */
    public static String fixLineBlank(String sql) {
        if (sql.indexOf("_ ") > 0 || sql.indexOf(" _") > 0) {
            return sql.replaceAll("_ ", "_").replaceAll(" _", "_");
        }
        return sql;
    }

    /**
     * 修复变量是关键字
     */
    public static String fixKeyWord(String sql) {
        Pattern pattern = Pattern.compile("#(\\s*(comment|start|end)\\s*)#", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String oldStr = matcher.group(1);
            String newStr = matcher.group(1) + "KEY";
            sql = sql.replace(oldStr, newStr);
        }
        return sql;
    }

    /**
     * 解决空变量的问题
     */
    public static String fixNonPar(String sql) {
        Pattern pattern = Pattern.compile("#(\\s*)#", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(sql);
        while (matcher.find()) {
            String oldStr = matcher.group();
            String newStr = "#PARAM#";
            sql = sql.replace(oldStr, newStr);
        }
        return sql;
    }

    /**
     * 处理durid新版本不认识的##
     * @param sql
     * @return
     */
    public static String formatdruidSharp(String sql) {
        StringBuffer sb=new StringBuffer();
        int index=0;
        boolean start=true;
        while(sql.indexOf("#",index)>0) {
            if (start) {
                sb.append(sql.substring(index, sql.indexOf("#")));
                sql = sql.substring(sql.indexOf("#")+1);
                sb.append(" ${");
                start = false;

            } else {
                sb.append(sql.substring(index, sql.indexOf("#")));
                sql = sql.substring(sql.indexOf("#")+1);
                sb.append("}");
                start = true;
            }
        }
        sb.append(sql.substring(index));
        return sb.toString();
    }


}
