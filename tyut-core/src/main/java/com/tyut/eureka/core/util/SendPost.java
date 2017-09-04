package com.tyut.eureka.core.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

/**
 * Created by SuperJohn on 2017/8/24.
 */
public class SendPost {
    public String getCheckCode_1(String cookie) throws IOException {
        double r = new Random().nextDouble();
        GetCheckCode getCheckCode = new GetCheckCode();
        String checkCode = null;
        Map<String,String> map = new HashMap<>();
        try {
            checkCode =getCheckCode.getCheckCode("http://202.207.247.44:8089/validateCodeAction.do?random="+ r,r+"laojiao.jpg",cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkCode;
    }

    public String sendPost(String username, String password, String checkCode, String CookieId) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://202.207.247.44:8089/loginAction.do");

        System.out.println("请输入验证码");
        //String checkCode = scanner.next();
        List<BasicNameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("zjh", username));
        formParams.add(new BasicNameValuePair("mm", password));
        formParams.add(new BasicNameValuePair("v_yzm", checkCode));
        httpPost.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpPost.setHeader("Accept-Language", "zh-CN,zh;q=0.8");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Cookie", CookieId);
//        httpPost.setHeader("Host", "202.207.247.49");//202.207.247.44:8089
        httpPost.setHeader("Host", "202.207.247.44:8089");
        httpPost.setHeader("Origin", "http://202.207.247.44:8089");
        httpPost.setHeader("Referer", "http://202.207.247.44:8089/");
        httpPost.setHeader("Upgrade-Insecure-Requests", "1");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(formParams, "gb2312");
        httpPost.setEntity(formEntity);
        //System.out.println("executing request: " + httpPost.getURI());
        //执行post请求
        CloseableHttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String resultPre = EntityUtils.toString(entity, "gb2312");
        Document doc = Jsoup.parse(resultPre);
        String result = doc.select(".errorTop").text();
//        if (entity != null) {
//            System.out.println("--------------------------------------");
//            System.out.println("Response content: " + EntityUtils.toString(entity, "gb2312"));
//            System.out.println("--------------------------------------");
//        }
        response.close();
        httpclient.close();
        return result;
    }

}
