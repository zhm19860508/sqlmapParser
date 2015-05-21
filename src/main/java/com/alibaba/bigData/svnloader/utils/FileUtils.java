package com.alibaba.bigData.svnloader.utils;

import org.dom4j.Element;

/**
 * Created with IntelliJ IDEA.
 * User: xiaoming.wm
 * Date: 14-1-14
 * Time: 下午4:21
 * 文件操作的一些相关方法
 */
public class FileUtils {
    /**
     * 获取文件名
     */
    public static String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
    }

    /**
     * 是否是xml文件
     */
    public static boolean isSqlMap(Element root) {
        if (root != null && root.getName().equalsIgnoreCase("sqlmap") ||
                root != null && root.getName().equalsIgnoreCase("mapper")) {
            return true;
        } else {
            return false;
        }
    }
}
