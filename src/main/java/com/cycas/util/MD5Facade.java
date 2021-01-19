package com.cycas.util;


import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author naxin
 * @Description:
 * @date 2021/1/819:16
 */
public class MD5Facade {

    /**
     * MD5,apache工具类DigestUtils
     */
    public static String encryptToMD5ByApache(String str, String charset) {

        return DigestUtils.md5DigestAsHex(str.getBytes(Charset.forName(charset)));
    }

    /**
     * MD5,Java实现
     */
    public static String md5ByJava(String text, String charset) throws NoSuchAlgorithmException {
        if (charset == null || charset.length() == 0) {
            charset = "UTF-8";
        }
        byte[] bytes = text.getBytes(Charset.forName(charset));
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(bytes);
        bytes = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            if ((bytes[i] & 0xff) < 0x10) {
                sb.append("0");
            }
            sb.append(Long.toString(bytes[i] & 0xff, 16));
        }
        return sb.toString().toLowerCase();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {

        String str = "Marydon";
        System.out.println(MD5Facade.encryptToMD5ByApache(str, "UTF-8"));
        System.out.println(MD5Facade.md5ByJava(str, "UTF-8"));
    }

}
