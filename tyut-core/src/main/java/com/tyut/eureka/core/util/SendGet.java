package com.tyut.eureka.core.util;

import com.tyut.eureka.core.pojo.HelpStudy;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SuperJohn on 2017/8/20.
 */
public class SendGet {
    //获取教务处公告列表
    public Elements getListFromJWC(String pageId) throws IOException {

        //略
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://jwc.tyut.edu.cn/detail.asp?bigid=7&Page=" + pageId);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(), "gb2312");
        Document doc = Jsoup.parse(result);
        org.jsoup.select.Elements tables = doc.select(".drlrimess tbody table");
        return tables;
    }

    //获取教务处公告详情
    public Elements getnoticeInfo(String id) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://jwc.tyut.edu.cn/info_show.asp?id=" + id + "&bigid=7&smallid=48");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(), "gb2312");
        Document doc = Jsoup.parse(result);
        Elements elements = doc.select(".drlrimess");
        return elements;
    }

    //获取岗位列表
    public List<HelpStudy> getHelpList(String url) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
        Document doc = Jsoup.parse(result);
        org.jsoup.select.Elements element = doc.getElementsByTag("td");
        List<HelpStudy> list = new ArrayList<>();
        for (int i = 108; i < element.size() - 1; i++) {
            HelpStudy helpStudy = new HelpStudy();
            String tds = element.get(i).text();
            //获取该条数据的url并做处理
            Elements urlPre = element.get(i).select("a");
            String url2 = urlPre.attr("href");
            helpStudy.setUrl("helpstudy.ngrok.cc/help/info/"+url2);
            //空两条td查询
            i = i + 2;
            Pattern p = Pattern.compile("^[\\u4e00-\\u9fa5]+");   //type
            Matcher matcher = p.matcher(tds);
            if (matcher.find()) {
                helpStudy.setType(matcher.group());
            }
            Pattern p1 = Pattern.compile("\\>.*\\(\\W+\\)");   //title
            Matcher matcher1 = p1.matcher(tds);
            if (matcher1.find()) {
                helpStudy.setTitle(matcher1.group());
            }
            Pattern p2 = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}\\s\\d{2}\\:\\d{2}");     //日期
            Matcher matcher2 = p2.matcher(tds);
            if (matcher2.find()) {
                helpStudy.setUptime(matcher2.group());
            }
            Pattern p3 = Pattern.compile("\\s[\\u4e00-\\u9fa5]{1,3}\\s");   //不限制
            Matcher matcher3 = p3.matcher(tds);
            if (matcher3.find()) {
                helpStudy.setSexlimit(matcher3.group());
            }
            Pattern p4 = Pattern.compile("\\s[\\u4e00-\\u9fa5]{4,5}");   //报名已经截至
            Matcher matcher4 = p4.matcher(tds);
            if (matcher4.find()) {
                helpStudy.setApplyStatus(matcher4.group());
            }
            Pattern p5 = Pattern.compile("\\s\\d*\\/\\d*");   //   1/1
            Matcher matcher5 = p5.matcher(tds);
            if (matcher5.find()) {
                helpStudy.setStationStatus(matcher5.group());
            }
            list.add(helpStudy);
        }
        return list;
    }

    //获取岗位详情
    public String getHelpInfo(String id) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://qgzx.tyut.edu.cn/th_gw.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jx=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jobid=7wdCsAC9utkaJRXcHHKLF7f96YCnhli0Tf3tT7F3MeeWlH7yQe0q4SxrfuaTDWLd6aRReh5dYvQ=");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
        Document doc = Jsoup.parse(result);
        org.jsoup.select.Elements element = doc.getElementsByTag("tbody");
//        for (int i = 12; i < element.size() - 1; i++) {
//            String tds = element.get(i).text();
//            System.out.println(tds);
//        }
        String tbody = String.valueOf(element.get(15));
        String resultInfo = tbody.replace("images/xz.png","http://qgzx.tyut.edu.cn/images/xz.png");
        return resultInfo;
    }
    //获取成绩查询页面
    public String sendGetCodeTable(String cookieId) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpGet httpGet = new HttpGet("http://202.207.247.49/gradeLnAllAction.do?type=ln&oper=qbinfo");//202.207.247.44:8089
        HttpGet httpGet = new HttpGet("http://202.207.247.44:8089/gradeLnAllAction.do?type=ln&oper=qbinfo");

        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cookie", cookieId);
//        httpGet.setHeader("Host", "202.207.247.49");
        httpGet.setHeader("Host", "202.207.247.44:8089");
//        httpGet.setHeader("Referer", "http://202.207.247.49/gradeLnAllAction.do?type=ln&oper=fa");
        httpGet.setHeader("Referer", "http://202.207.247.44:8089/gradeLnAllAction.do?type=ln&oper=fa");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(), "gb2312");
        return result;
    }


    //查询课表
    public String sendGetClassTable(String cookieId) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://202.207.247.44:8089/xkAction.do?actionType=6");
        httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpGet.setHeader("Connection", "keep-alive");
        httpGet.setHeader("Cookie", cookieId);//@17d0685f
//            httpGet.setHeader("Cookie","17d0685f");
//        httpGet.setHeader("Host", "202.207.247.49");
        httpGet.setHeader("Host", "202.207.247.44:8089");
//        httpGet.setHeader("Referer", "http://202.207.247.49/gradeLnAllAction.do?type=ln&oper=fa");
        httpGet.setHeader("Referer", "http://202.207.247.44:8089/gradeLnAllAction.do?type=ln&oper=fa");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String result = EntityUtils.toString(httpResponse.getEntity(), "gb2312");

        //System.out.println("Response content: " +result);
        return result;
    }
}
