package com.cycas.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha256Utils {

    public Sha256Utils() {
    }

    public static String processSHA512(String pw, String salt, int rounds) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
            throw new RuntimeException("No Such Algorithm");
        }

        String result = hashPw(md, pw, salt, rounds);
        System.out.println(result);
        return result;
    }

    private static String hashPw(MessageDigest md, String pw, String salt, int rounds) {
        String appendedSalt = '{' + salt + '}';

        byte[] bSalt;
        byte[] bPw;
        try {
            bSalt = appendedSalt.getBytes("ISO-8859-1");
            bPw = pw.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException var10) {
            throw new RuntimeException("Unsupported Encoding", var10);
        }

        byte[] digest = run(md, bPw, bSalt);
        System.out.println(Base64.encodeBase64String(digest));

        for(int i = 1; i < rounds; ++i) {
            byte[] d = ArrayUtils.addAll(digest, bPw);
            digest = run(md, d, bSalt);
        }

        return Base64.encodeBase64String(digest);
    }

    private static byte[] run(MessageDigest md, byte[] input, byte[] salt) {
        md.update(input);
        return md.digest(salt);
    }

}
