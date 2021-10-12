package org.choviwu.top.qg.util;

import java.security.MessageDigest;

/**
 * @author ChoviWu
 * @date 2019/1/4
 * Description :
 */
public class MD5Utils {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(resultString.getBytes("UTF-8"));
            resultString = byteArrayToHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
    /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String md5(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(resultString.getBytes("UTF-8"));
            resultString = byteArrayToHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    public static void main(String[] args) {
        System.out.println(MD5Encode("1443041003"+MD5Encode("wuchaowei").substring(0,30).toUpperCase()+"13683").toUpperCase().substring(0,30));

        System.out.println(md5(md5("r4d7".toUpperCase()).substring(0,30).toUpperCase()+"13683").substring(0,30).toUpperCase());
        //4EB1A7BB3B062AD85D73A9C9A8F14E
        //405D8F7ADE48334C71BB8402072F58
        //3F203C43B0B874B10064BB5222270E
        //3F203C43B0B874B10064BB5222270E
    }
}
