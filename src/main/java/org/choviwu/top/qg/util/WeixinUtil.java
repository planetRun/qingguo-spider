package org.choviwu.top.qg.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Map;

public class WeixinUtil {

    public static final String appId = "wxdb60881fc239da0d";

    public static final String secret = "0e0be21488dd80894af7d9d21bc3731a";
//    public static final String secret = "8f6c11fb08f3f7a0d8a505883b43df01";


    /**
     * 微信小程序
     * 获取openid的方法
     * @param  appId
     * @param  appSecret
     * @param  code
     * @return  JSONObject对象//含有openid、session_key、unionid的键值对
     * @author Created by chenrongkang On 2018/8/20
     * */
    public static String getXCXOpenId(String appId, String appSecret, String code) {
        JSONObject obj = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID" +
                "&secret=SECRET&js_code=JSCODE" +
                "&grant_type=authorization_code";
        requestUrl = requestUrl.replace("APPID", appId);
        requestUrl = requestUrl.replace("SECRET", appSecret);
        requestUrl = requestUrl.replace("JSCODE", code);
        try {
            String res = HttpUtil.createPost(requestUrl).execute().body(); //http模拟发送请求，返回json字符串
            Map<String, String> map = JSONObject.parseObject(res, Map.class); //解析json字符串
            if (null != map && !map.isEmpty()) {
                try {
                    System.out.println(JSON.toJSONString(map));
                    if (!map.containsKey("errcode")) {
                        return map.get("openid");
                    } else {
                        String errorCode = String.valueOf(map.get("errcode"));
                        if ("40163".equals(errorCode)) {
                            throw new RuntimeException("链接已过期");
                        } else if ("40029".equals(errorCode)) {
                            throw new RuntimeException("code无效");
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return "";
    }
}
