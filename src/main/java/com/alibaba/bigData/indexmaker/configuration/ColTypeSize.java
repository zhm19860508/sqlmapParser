package com.alibaba.bigData.indexmaker.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-5-5
 * Time: 下午4:46
 * 表字段对应的大小
 */
public class ColTypeSize {
    public static final int TINYINT_SIZE = 1;
    public static final int SMALLINT_SIZE = 2;
    public static final int MEDIUMINT_SIZE = 3;
    public static final int INT_SIZE = 4;
    public static final int BIGINT_SIZE = 8;
    public static final int FLOAT_SIZE = 4;
    public static final int DOUBLE_SIZE = 8;
    public static final int REAL_SIZE = 8;
    public static final int DATE_SZIE = 3;
    public static final int DATATIME_SIZE = 4;
    public static final int TIMESTAMP_SIZE = 4;
    public static final int CHAR_SIZE = 1;
    public static final int VARCHAR_SIZE = 1;

    public static final int MAX_IDX_SIZE = 767;


    public static int getColTypeSize(String colType) {
        int size = 0;
        if (colType.toLowerCase().contains("int")) {
            size = BIGINT_SIZE;
        } else if (colType.toLowerCase().contains("char")) {
            int len = Integer.parseInt(colType.replaceAll("^.*\\(", "").replaceAll("\\).*", ""));
            size = CHAR_SIZE * len;
        } else if (colType.toLowerCase().contains("float")) {
            size = FLOAT_SIZE;
        } else if (colType.toLowerCase().contains("double")) {
            size = DOUBLE_SIZE;
        } else if (colType.toLowerCase().equals("date")) {
            size = DATE_SZIE;
        } else if (colType.toLowerCase().equals("datetime")) {
            size = DATATIME_SIZE;
        } else if (colType.toLowerCase().equals("timestamp")) {
            size = TIMESTAMP_SIZE;
        }
        return size;
    }

}
