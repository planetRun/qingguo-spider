package org.choviwu.top.qg.score;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.choviwu.top.qg.ex.CrudException;
import org.choviwu.top.qg.util.ProxyThreadLocal;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.choviwu.top.qg.entity.CourseScore;
import org.choviwu.top.qg.ex.ExceptionEnum;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.choviwu.top.qg.util.MD5Utils.MD5Encode;
import static org.choviwu.top.qg.util.MD5Utils.md5;

/***
 *
 **/
public class JwcRequest {

    static Map map;

    static String[] xnxqTemplate = new String[] {
            "学年第一学期",
            "学年第二学期"
    };

    static {
        map = Maps.newHashMap();
        map.put("typeName", "");
        map.put("Sel_Type", "STU");
        map.put("txt_pewerwedsdfsdff", "");
        map.put("txt_sdertfgsadscxcadsads", "");
        map.put("sbtState", "");
        map.put("pcInfo", "");
        map.put("validcodestate", 0);
    }

    public static HttpCookie login(String id, String password,String school) {
        List list = ValidateImage.getImage();
        HttpCookie cookie = ((HttpCookie) list.get(0));
        String validate = (String) list.get(1);
        String xuehao = MD5Encode(id + MD5Encode(password).substring(0, 30).toUpperCase() + school).toUpperCase().substring(0, 30);
        String yzm = md5(md5(validate.toUpperCase()).substring(0, 30).toUpperCase() + school).substring(0, 30).toUpperCase();
        Map<String, String> viewState = getViewState();
        HttpResponse response = HttpUtil.createPost(CommonLog.CACHE_MAP.get("login_url")).form(map)
                .setProxy(ProxyThreadLocal.get())
                .form("dsdsdsdsdxcxdfgfg", xuehao)
                .form("fgfggfdgtyuuyyuuckjg", yzm)
                .form("__VIEWSTATE", viewState.get("__VIEWSTATE"))
                .form("__EVENTVALIDATION", viewState.get("__EVENTVALIDATION"))
                .form("txt_asmcdefsddsd", id)
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Host", ValidateImage.parseDomain(CommonLog.CACHE_MAP.get("login_url")))
                .header("Referer", CommonLog.CACHE_MAP.get("login_url"))
                .header("Upgrade-Insecure-Requests", "1")
                .header("User-Agent", Constant.USER_AGENT)
                .header("Cookie", cookie.toString())
                .execute();
        String body = response.body();
        if (body.contains(Constant.VALIDATE_ERROR)) {
            login(id, password,school);
        } else if (body.contains("帐号或密码不正确")) {
            throw new CrudException(ExceptionEnum.account_or_password_error);
        }
        System.out.println("2:>>>" + cookie);

        return cookie;
    }

    public static Map<String,String> getViewState() {
        Map<String,String> map = new HashMap<>();

        try {
            final String res = HttpUtil.createGet(CommonLog.CACHE_MAP.get("login_url"))
                    .header("user-agent", Constant.USER_AGENT)
                    .header("Accept-Encoding", "gzip, deflate")
                    .setFollowRedirects(false)
                    .setProxy(ProxyThreadLocal.get())
                    .execute().body();
            Document result = Jsoup.parse(res);
            Elements elements = result.getElementsByTag("input");
            String viewState = elements.get(0).attr("value");
            String __EVENTVALIDATION = elements.get(1).attr("value");
            map.put("__VIEWSTATE", viewState);
            map.put("__EVENTVALIDATION", __EVENTVALIDATION);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<CourseScore> getCourseScores(HttpCookie cookies, String xnxq, String xn) {
        List<CourseScore> courseScores = Lists.newArrayList();
        try {
            HttpResponse response = HttpUtil.createPost(CommonLog.CACHE_MAP.get("score_url"))
                    .setProxy(ProxyThreadLocal.get())
                    .header("Accept", "image/webp,image/apng,image/*,*/*;q=0.8")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Host", ValidateImage.parseDomain(CommonLog.CACHE_MAP.get("score_url")))
//                    .header("Referer", "/jwweb/xscj/Stu_cjfb.aspx")
                    .header("Upgrade-Insecure-Requests", "1")
                    .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                    .header("Cookie", cookies.toString())
                    .cookie(cookies.toString())
//                    .form("sel_xn", xn).form("sel_xq", xq)
                    .form("SelXNXQ", 0)
                    .form("submit","")
                    .execute();
            System.out.println(response.body());
            Elements elements = Jsoup.parse(response.body()).children();
            Elements scores = elements.get(0).getElementById("ID_Table").getElementsByTag("tr");
            String newXnxq = "";
            for (int i = 0; i < scores.size(); i++) {
                if(i>=scores.size()-2){
                    return courseScores;
                }
                Elements childrens = scores.get(i).children();
                xnxq = childrens.get(0).text();
                int xq = 0;
                for (int i1 = 0; i1 < xnxqTemplate.length; i1++) {
                    if (xnxq.contains(xnxqTemplate[i1])) {
                        xnxq = xnxq.replace(xnxqTemplate[i1], "");
                        xq = i1;
                        newXnxq = xnxq.split("-")[0] + xq;
                    }
                }

                String course = childrens.get(1).text();
                String score = childrens.last().text();
                CourseScore courseScore = new CourseScore();
                courseScore.setAddtime(new BigDecimal(System.currentTimeMillis()))
                        .setCourseCredit(Double.valueOf(childrens.get(2).text()))
                        .setCourseName(course)
                        .setCourseType(childrens.get(3).text())
                        .setNatureRead(childrens.get(5).text())
                        .setScore(score)
                        .setTestType(childrens.get(4).text())
                        .setXnxq(newXnxq);
                courseScores.add(courseScore);
            }
            return courseScores;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return courseScores;
        }
    }
}
