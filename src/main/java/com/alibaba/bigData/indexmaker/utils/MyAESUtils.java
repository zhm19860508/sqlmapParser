package com.alibaba.bigData.indexmaker.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;

/**
 * Created by xiaoming.wm on 2014/8/22.
 * Aes的工具类
 */
public class MyAESUtils {
    private final static String ALGORITHM_AES = "AES";

    static String KEY_SRC = "p5DLnscDtBQ7gHcNng2qiB5j";
    static BigDecimal MOD_NUM = new BigDecimal(251);
    static byte[] Key;

    private static byte[] getKey() {
        if (Key == null) {
            try {
                //String keyConfig=PropertyConfig.getProperty("encrypt.dbkey");
                String keyConfig = "WQ7317e7SMgjnY9ye0Mp";
                String keyDb = getDbKey();
                Key = buildKey((KEY_SRC + keyDb + keyConfig).getBytes());
            } catch (Exception e) {
                throw new RuntimeException("获取解密Key出错" + e.getMessage());
            }
        }
        return Key;
    }

    private static String getDbKey() {
        //String encryptDbKey=ConfigUtils.getConfigValue("ENCRYPT_DBKEY");
        String encryptDbKey = "4EK85C944WbkAhIWnkZs";
        return encryptDbKey;
    }

    private static byte[] buildKey(byte[] key) {
        byte[] result = new byte[16];
        BigDecimal mult_num = new BigDecimal(1);
        for (int i = 0; i < key.length; i++) {
            mult_num = mult_num.multiply(new BigDecimal(key[i]));
        }
        BigDecimal tmpbigd = mult_num;
        for (int i = 0; i < 16; i++) {
            BigDecimal remain = tmpbigd.remainder(MOD_NUM);
            tmpbigd = tmpbigd.divideToIntegralValue(MOD_NUM);
            result[i] = remain.byteValue();
        }
        return result;
    }

    public static String encrypt(String data, byte[] keybytes) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(keybytes, ALGORITHM_AES);
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            byte[] encrypted = cipher.doFinal(data.getBytes());
            return encryptBASE64(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("数据加密出错" + e.getMessage());
        }
    }

    public static String encrypt(String data) {
        return encrypt(data, getKey());
    }

    public static String decrypt(String encryptedStr) {
        return decrypt(encryptedStr, getKey());
    }

    public static String decrypt(String encryptedStr, byte[] keybytes) {
        try {
            byte[] tmpdata = decryptBASE64(encryptedStr);
            SecretKeySpec skeySpec = new SecretKeySpec(keybytes, ALGORITHM_AES);
            Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decrypted = cipher.doFinal(tmpdata);
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("数据解密出错" + e.getMessage());
        }
    }

    public static String encryptBASE64(byte[] key) throws Exception {
        //return (new BASE64Encoder()).encodeBuffer(key);
        return new String(Base64.encodeBase64(key));
    }

    public static byte[] decryptBASE64(String key) throws Exception {
        //return (new BASE64Decoder()).decodeBuffer(key);
        return Base64.decodeBase64(key.getBytes());
    }

    public static void main(String[] args) throws Exception {
//        String data = "alibaba1949";
//        String key = "0123456789ABCDEF";
//        System.out.println("密钥为：" + key);
//        long lStart = System.currentTimeMillis();
//        // 加密
//        String encrypted = encrypt(data);
//        System.out.println("原文" + data);
//        System.out.println("加密后：" + encrypted);
//        long lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("加密耗时" + lUseTime + "");
//        System.out.println();
//        // 解密
//        lStart = System.currentTimeMillis();
//        String decrypted = decrypt(encrypted);// 解密串
//        System.out.println("解密后" + decrypted);
//        lUseTime = System.currentTimeMillis() - lStart;
//        System.out.println("解密耗时" + lUseTime + "");
    }

}
