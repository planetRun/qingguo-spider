package org.choviwu.top.qg.util;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author chovi.wu
 * @Description
 * @create 2021-10-19 9:32
 */
public class ProxyPoolUtils {

    public static List<Proxy> proxies = new ArrayList<>();

    static String url = "https://www.kuaidaili.com/free/inha/%d/";

    public static void putProxy(){
        for (int i = 1; i <= 2; i++) {
            try {
                final Document document = Jsoup.connect(String.format(url, i)).timeout(300000).get();
                final Element body = document.body();
                final Element table = body.getElementsByTag("table").first();
                final Element tbody = table.getElementsByTag("tbody").first();
                final Elements children = tbody.children();
                for (Element child : children) {
                    final String host = child.getElementsByTag("td").first().text();
                    final int port = Integer.parseInt(child.getElementsByTag("td").get(1).text());
                    final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
                    if (validate(proxy)) {
                        proxies.add(proxy);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean validate(Proxy proxy) {
        final HttpResponse execute = HttpUtil.createGet("http://ip-api.com/json/?lang=zh-CN").setProxy(proxy)
                .execute();
        System.out.println(execute.body());
        if (execute.isOk()) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress("125.87.95.199", 3256));
        validate(proxy);
    }

}
