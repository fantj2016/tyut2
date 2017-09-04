package com.tyut.eureka.core.util;

import org.jsoup.Jsoup;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by SuperJohn on 2017/8/20.
 */
public class GetCookieUtil {
    //教务处公告的 cookie
    public String getCookieFromNotice() throws IOException {
        String cookiePre = String.valueOf(Jsoup.connect("http://jwc.tyut.edu.cn/Index.asp").execute().cookies());
        String cookie1 ="UM_distinctid=15cf2c5fe52197-0c67f0e7219924-5393662-144000-15cf2c5fe541cb; "+ cookiePre.replace("{", "").replace("}", "");
        return cookie1;
    }
}
