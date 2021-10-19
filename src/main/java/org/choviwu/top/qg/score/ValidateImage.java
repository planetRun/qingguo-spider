package org.choviwu.top.qg.score;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import org.choviwu.top.qg.util.ProxyThreadLocal;


/***
 *
 **/
public class ValidateImage {

    /**
     * 获取图片
     *
     * @return
     */
    public static List getImage() {
        //
        String validateUrl = CommonLog.CACHE_MAP.get(Constant.VALIDATE_URL);
        String url = validateUrl + System.currentTimeMillis() % 1000;
//        http://www.xacxxy.com:88/
        String host = parseDomain(validateUrl);
        try {
            HttpResponse response = HttpUtil.createGet(url).setProxy(ProxyThreadLocal.get())
                    .header("Accept", "image/webp,image/apng,image/*,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "zh-CN,zh;q=0.9")
                    .header("Connection", "keep-alive")
                    .header("Host", host)
                    .header("Referer", CommonLog.CACHE_MAP.get(Constant.LOGIN_URL))
                    .header("User-Agent", Constant.USER_AGENT)
                    .execute();
            byte[] bytes = response.bodyBytes();
            List list = Lists.newArrayList();

            list.add(response.getCookies().get(0));
            String validateCode = executeTess4J(bytes);
            list.add(validateCode);
            return list;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String executeTess4J(byte[] imgUrl) {
        String url = "https://www.paddlepaddle.org.cn/paddlehub-api/image_classification/chinese_ocr_db_crnn_mobile";
        final String encode = Base64.encode(imgUrl);

        Map map = new HashMap();
        map.put("image", encode);
        final String string = JSON.toJSONString(map);
        final HttpResponse execute = HttpUtil.createPost(url)

                .header("Host", "www.paddlepaddle.org.cn")
                .header("Origin", "https://www.paddlepaddle.org.cn")
                .header("Referer", "https://www.paddlepaddle.org.cn/hub/scene/ocr")
                .header("sec-ch-ua-platform", "Windows")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36")
                .body(string)
                .execute();
        final String body = execute.body();
        System.out.println(body);
        try {
            JSONObject jsonObject = JSON.parseObject(body);
            JSONArray result = jsonObject.getJSONArray("result");
            JSONArray data = result.getJSONObject(0).getJSONArray("data");
            String text = data.getJSONObject(0).getString("text");
            return text;
        } catch (Exception e) {
            return "";
        }
    }

    public static void main(String[] args) {

    }


    public static String parseDomain(String url) {
        String regex = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
        Pattern pattern = Pattern.compile(regex);
        if (pattern.matcher(url).matches()) {
            return URI.create(url).getHost();
        } else {
            return url;
        }
    }


}
