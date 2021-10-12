package org.choviwu.top.qg.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class CookieUtils {


    public static Document parseUrl(String url){
        AtomicBoolean flag = new AtomicBoolean(false);
        AtomicInteger count = new AtomicInteger(0);
        Document doc = null;
        while (!flag.get()){
            try {
                if(count.get()>5){
                    break;
                }
                doc = Jsoup.connect(url).timeout(10000)
                        .header("sec-fetch-mode","navigate")
                        .header("sec-fetch-site","none").header("sec-fetch-user","?1")
                        .header("upgrade-insecure-requests","1")
                        .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36")
                        .get();
                flag.set(true);

            } catch (IOException e) {
                e.printStackTrace();
                count.incrementAndGet();
            }
        }
        return doc;
    }
}
