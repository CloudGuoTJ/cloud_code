package com.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Atlantique on 2015/9/3.
 */
public class MD5Util {
    public static String makeMd5Sum(byte[] srcContent) {
        if (srcContent == null) {
            return null;
        }

        String strDes = null;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(srcContent);
            strDes = bytes2Hex(md5.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] byteArray) {
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (byteArray[i] >= 0 && byteArray[i] < 16) {
                strBuf.append("0");
            }
            strBuf.append(Integer.toHexString(byteArray[i] & 0xFF));
        }
        return strBuf.toString();
    }
}
