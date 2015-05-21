package com.alibaba.bigData.svnloader.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: zongdan
 * Date: 7/3/12
 * Time: 4:58 PM
 */
public class Md5Utils {
    private static final Log logger = LogFactory.getLog(Md5Utils.class);
    public static String md5(String text) {
        return md5(text, "GBK");
    }

    public static String md5(String text, String charset) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }
        try {
            msgDigest.update(text.getBytes(charset));
        } catch (Exception e) {
            logger.error("md5:" + text +" charset:" + charset, e);
            return null;
        }
        byte[] bytes = msgDigest.digest();
        byte tb;
        char low;
        char high;
        char tmpChar;
        StringBuilder md5Str = new StringBuilder(32);
        for (byte aByte : bytes) {
            tb = aByte;
            tmpChar = (char) ((tb >>> 4) & 0x000f);
            if (tmpChar >= 10) {
                high = (char) (('a' + tmpChar) - 10);
            } else {
                high = (char) ('0' + tmpChar);
            }
            md5Str.append(high);
            tmpChar = (char) (tb & 0x000f);
            if (tmpChar >= 10) {
                low = (char) (('a' + tmpChar) - 10);
            } else {
                low = (char) ('0' + tmpChar);
            }
            md5Str.append(low);
        }
        return md5Str.toString();
    }
}
