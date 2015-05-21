package com.alibaba.bigData.test;

import com.alibaba.bigData.main.IbatisParser;

public class Test {

    public static void main(String[] args) {
        IbatisParser ibatisParser = new IbatisParser();

        // 各种文件路径
        String oracle_ibatis = System.getProperty("user.dir")
                               + "/src/test/resources/com/alibaba/bigData/sqlmap/oracle/ibatis";

        String mysql_ibatis = System.getProperty("user.dir")
                              + "/src/test/resources/com/alibaba/bigData/sqlmap/mysql/ibatis";
        String mysql_mybatis = System.getProperty("user.dir")
                               + "/src/test/resources/com/alibaba/bigData/sqlmap/mysql/mybatis";
        String oracle_mybatis = System.getProperty("user.dir")
                                + "/src/test/resources/com/alibaba/bigData/sqlmap/oracle/mybatis";

        // ibatisParser.beyondGetAllSqls(oracle_ibatis, "oracle", "ibatis", "");
        // ibatisParser.beyondGetAllSqls(mysql_ibatis, "mysql", "ibatis", "");
        // ibatisParser.beyondGetAllSqls(mysql_mybatis, "mysql", "mybatis", "");
        ibatisParser.beyondGetAllSqls(oracle_mybatis, "oracle", "mybatis", "");
    }

}
