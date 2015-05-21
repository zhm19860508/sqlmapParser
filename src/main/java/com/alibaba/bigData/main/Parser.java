package com.alibaba.bigData.main;

public class Parser {

    public static void main(String[] args) {
        IbatisParser ibatisParser = new IbatisParser();
        ibatisParser.beyondGetAllSqls(args[0], args[1], args[2], args[3]);
    }
}
