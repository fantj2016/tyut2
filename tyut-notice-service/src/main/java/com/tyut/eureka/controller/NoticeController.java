package com.tyut.eureka.controller;

import com.tyut.eureka.core.pojo.Notice;
import com.tyut.eureka.core.util.GetCookieUtil;
import com.tyut.eureka.core.util.SendGet;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SuperJohn on 2017/8/20.
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
    //获取教务处公告列表
    @GetMapping("/list/{page}")
    @ResponseBody
    public Map<String, Object> listcrawl(HttpSession session,
                                         @PathVariable("page")String pageId) throws IOException {

        SendGet sendGet = new SendGet();
        org.jsoup.select.Elements tables = sendGet.getListFromJWC(pageId);
        List<Notice> noticeList =new ArrayList<>();
        //对html中的50个table标签做处理
        for (int i=0;i<tables.size()-1;i++){
            Notice notice = new Notice();
            Element table = tables.get(i);
            org.jsoup.select.Elements spans = table.getElementsByTag("span");
            String url = String.valueOf(spans.first().select("a").attr("href"));
            //对url进行分割，拿到id值
            String urlPre1 = url.split("\\?")[1];
            String urlPre2 = urlPre1.split("=")[1];
            String urlId = urlPre2.split("&")[0];
            notice.setId(Integer.valueOf(urlId));
            String title = spans.first().text();
            String uptime = spans.get(1).text();
            notice.setTitle(title);
            //如果服务器改动，需要改这里
            notice.setUrl("tyut.ngrok.cc/notice/info/"+urlId);
            notice.setUpdate(uptime);
            noticeList.add(notice);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("status","3");
        map.put("info",noticeList);
        return map;
    }
    //获取教务处公告详情
    @GetMapping("/info/{id}")
    public String infocrawl(@PathVariable("id")String id) throws IOException {
        SendGet sendGet = new SendGet();
        String elements = String.valueOf(sendGet.getnoticeInfo(id));
        String result = elements.replace("/uploads","http://jwc.tyut.edu.cn/uploads");
        return result;
    }
}
