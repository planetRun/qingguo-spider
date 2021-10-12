package org.choviwu.top.qg.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
@Component
public class ReflectUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ReflectUtils.class);

    @Value("${tencent.appKey}")
    private String appKey;

        public   String getSignature(Map<String, Object> params)  {
            Map<String, Object> sortedParams = new TreeMap<>(params);
            Set<Map.Entry<String, Object>> entrys = sortedParams.entrySet();
            StringBuilder baseString = new StringBuilder();
            for (Map.Entry<String, Object> param : entrys) {
                if (param.getValue() != null && !"".equals(param.getKey().trim()) &&
                        !"sign".equals(param.getKey().trim()) && !"".equals(param.getValue())) {
                    try {
                        baseString.append(param.getKey().trim()).append("=")
                                .append(URLEncoder.encode(param.getValue().toString(), "UTF-8")).append("&");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (baseString.length() > 0) {
                baseString.deleteCharAt(baseString.length() - 1).append("&app_key=")
                        .append(appKey);
            }
            System.out.println(baseString);
            try {
                String sign = MD5Utils.MD5Encode(baseString.toString());
                System.out.println("sign:" + sign.toUpperCase());
                return sign.toUpperCase();
            } catch (Exception ex) {
                return null;
            }
        }
}
