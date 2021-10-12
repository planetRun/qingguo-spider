package org.choviwu.top.qg.util;

public class ShortUrlUtils {

    private static final String STR_CODE = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String genShorUrl(){
        try {

            //生成当前时间戳
            String ret = Strings.encode(System.currentTimeMillis()+Strings.genRandomNumber(4),7);
            System.out.println(ret);

            return ret;
        }catch (Exception e){
            System.out.println(">>>>>>>>>>"+e);
            return genShorUrl();
        }
    }

    public static void main(String[] args) {
        genShorUrl();
    }



}
