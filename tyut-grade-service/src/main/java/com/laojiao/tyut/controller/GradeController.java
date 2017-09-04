package com.laojiao.tyut.controller;
import com.tyut.eureka.core.mapper.GradeMapper;
import com.tyut.eureka.core.pojo.ClassInfo;
import com.tyut.eureka.core.pojo.ClassTable;
import com.tyut.eureka.core.pojo.Subject;
import com.tyut.eureka.core.util.SendGet;
import com.tyut.eureka.core.util.SendPost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SuperJohn on 2017/8/19.
 */
@RestController
public class GradeController {

    @Autowired
    private GradeMapper gradeMapper;

    @PostMapping("/login")
    public Map<String, String> login(@RequestParam("username")String username,
                                     @RequestParam("password")String password,
                                     HttpSession session) throws IOException {
        String cookiePre = String.valueOf(Jsoup.connect("http://202.207.247.44:8089").execute().cookies());
        String cookie = cookiePre.replace("{", "").replace("}", "");
        SendPost sendPost = new SendPost();
        String checkCode = sendPost.getCheckCode_1(cookie);
        System.out.println(checkCode);
        String result = String.valueOf(sendPost.sendPost(username,password, checkCode, cookie));
        if (result.contains("你输入的验证码错误，请您重新输入！")) {
            for (int i = 0; i < 20; i++) {
                String checkCode2Pre = sendPost.getCheckCode_1(cookie);
                String checkCode2 = checkCode2Pre.substring(0, 4);
                String result2 = String.valueOf(sendPost.sendPost(username, password, checkCode2, cookie));
                if (result2.contains("你输入的验证码错误，请您重新输入！"))
                    continue;
                else if (result2.contains("您的密码不正确，请您重新输入！")){
                    System.out.println("您的密码不正确，请您重新输入");
                    Map<String,String> map = new HashMap<>();
                    map.put("message","密码错误");
                    return map;
                }else
                    System.out.println(result2 + "登录成功: "+username+"验证码: "+checkCode2+" cookie："+cookie);
                break;
            }
        }
        session.setAttribute("cookie",cookie);
        Map<String,String> map = new HashMap<>();
        map.put("message","登陆成功");
        map.put("username",username);
        return map;
    }
//成绩查询
    @PostMapping("/grade")
    public Map<String, Object> selectCode(@RequestParam("username")String username,
                                          @RequestParam("password")String password
    ) throws IOException {
        String cookiePre = String.valueOf(Jsoup.connect("http://202.207.247.44:8089").execute().cookies());
        String cookie = cookiePre.replace("{", "").replace("}", "");
        SendPost sendPost = new SendPost();
        String checkCode = sendPost.getCheckCode_1(cookie);
        System.out.println(checkCode);
        String result = String.valueOf(sendPost.sendPost(username,password, checkCode, cookie));
        if (result.contains("你输入的验证码错误，请您重新输入！")) {
            for (int i = 0; i < 20; i++) {
                String checkCode2Pre = sendPost.getCheckCode_1(cookie);
                String checkCode2 = checkCode2Pre.substring(0, 4);
                String result2 = String.valueOf(sendPost.sendPost(username, password, checkCode2, cookie));
                if (result2.contains("你输入的验证码错误，请您重新输入！"))
                    continue;
                else if (result2.contains("您的密码不正确，请您重新输入！")){
                    System.out.println("您的密码不正确，请您重新输入");
                }else
                    System.out.println(result2 + "登录成功: "+username+"验证码: "+checkCode2+" cookie："+cookie);
                break;
            }
        }
        SendGet sendGet = new SendGet();
        String result2 = sendGet.sendGetCodeTable(cookie);
        Document doc = Jsoup.parse(result2);
        Elements trs = doc.select(".odd");
        List<Subject> list = new ArrayList<>();
        for (Element tr:trs){
            Subject sj = new Subject();
            Elements tds = tr.getElementsByTag("td");
            String subject = tds.get(2).text();
            sj.setName(subject);
            String credit = tds.get(4).text();
            sj.setCredit(credit);
            String property = tds.get(5).text();
            sj.setProperty(property);
            String score = tds.get(6).text();
            sj.setScore(score);
            list.add(sj);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("message","查询成功");
        map.put("info",list);
        return map;
    }
    @PostMapping("/class")
    public Map<String, Object> getClass(@RequestParam("username")String username,
                                        @RequestParam("password")String password) throws IOException {
        String cookiePre = String.valueOf(Jsoup.connect("http://202.207.247.44:8089").execute().cookies());
        String cookie = cookiePre.replace("{", "").replace("}", "");
        SendPost sendPost = new SendPost();
        String checkCode = sendPost.getCheckCode_1(cookie);
        System.out.println(checkCode);
        String result = String.valueOf(sendPost.sendPost(username,password, checkCode, cookie));
        if (result.contains("你输入的验证码错误，请您重新输入！")) {
            for (int i = 0; i < 20; i++) {
                String checkCode2Pre = sendPost.getCheckCode_1(cookie);
                String checkCode2 = checkCode2Pre.substring(0, 4);
                String result2 = String.valueOf(sendPost.sendPost(username, password, checkCode2, cookie));
                if (result2.contains("你输入的验证码错误，请您重新输入！"))
                    continue;
                else if (result2.contains("您的密码不正确，请您重新输入！")){
                    System.out.println("您的密码不正确，请您重新输入");
                }else
                    System.out.println(result2 + "登录成功: "+username+"验证码: "+checkCode2+" cookie："+cookie);
                break;
            }
        }
        SendGet sendGet = new SendGet();
        String resultClass = sendGet.sendGetClassTable(cookie);
        Document doc = Jsoup.parse(resultClass);
        Elements trs = doc.select(".pageAlign tr");
        List<ClassTable> list = new ArrayList<>();
        for (int i=1;i<2;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(1).text());
            table.setMonday(tds.get(2).text());
            table.setTuesday(tds.get(3).text());
            table.setWednesday(tds.get(4).text());
            table.setThursday(tds.get(5).text());
            table.setFriday(tds.get(6).text());
            table.setSaturday(tds.get(7).text());
            table.setSunday(tds.get(8).text());
            list.add(table);
        }
        for (int i=2;i<5;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(0).text());
            table.setMonday(tds.get(1).text());
            table.setTuesday(tds.get(2).text());
            table.setWednesday(tds.get(3).text());
            table.setThursday(tds.get(4).text());
            table.setFriday(tds.get(5).text());
            table.setSaturday(tds.get(6).text());
            table.setSunday(tds.get(7).text());
            list.add(table);
        }
        for (int i=6;i<7;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(1).text());
            table.setMonday(tds.get(2).text());
            table.setTuesday(tds.get(3).text());
            table.setWednesday(tds.get(4).text());
            table.setThursday(tds.get(5).text());
            table.setFriday(tds.get(6).text());
            table.setSaturday(tds.get(7).text());
            table.setSunday(tds.get(8).text());
            list.add(table);
        }
        for (int i=7;i<10;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(0).text());
            table.setMonday(tds.get(1).text());
            table.setTuesday(tds.get(2).text());
            table.setWednesday(tds.get(3).text());
            table.setThursday(tds.get(4).text());
            table.setFriday(tds.get(5).text());
            table.setSaturday(tds.get(6).text());
            table.setSunday(tds.get(7).text());
            list.add(table);
        }
        for (int i=11;i<12;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(1).text());
            table.setMonday(tds.get(2).text());
            table.setTuesday(tds.get(3).text());
            table.setWednesday(tds.get(4).text());
            table.setThursday(tds.get(5).text());
            table.setFriday(tds.get(6).text());
            table.setSaturday(tds.get(7).text());
            table.setSunday(tds.get(8).text());
            list.add(table);
        }
        for (int i=12;i<14;i++){
            ClassTable table = new ClassTable();
            Elements tds = trs.get(i).getElementsByTag("td");
            table.setTime(tds.get(0).text());
            table.setMonday(tds.get(1).text());
            table.setTuesday(tds.get(2).text());
            table.setWednesday(tds.get(3).text());
            table.setThursday(tds.get(4).text());
            table.setFriday(tds.get(5).text());
            table.setSaturday(tds.get(6).text());
            table.setSunday(tds.get(7).text());
            list.add(table);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("status","success");
        map.put("table",list);
        return map;
    }

    @PostMapping("/classArrange")
    public Map<String, Object> getClassArrange(@RequestParam("username")String username,
                                               @RequestParam("password")String password)throws IOException {

        String cookiePre = String.valueOf(Jsoup.connect("http://202.207.247.44:8089").execute().cookies());
        String cookie = cookiePre.replace("{", "").replace("}", "");
        SendPost sendPost = new SendPost();
        String checkCode = sendPost.getCheckCode_1(cookie);
        System.out.println(checkCode);
        String result = String.valueOf(sendPost.sendPost(username,password, checkCode, cookie));
        if (result.contains("你输入的验证码错误，请您重新输入！")) {
            for (int i = 0; i < 20; i++) {
                String checkCode2Pre = sendPost.getCheckCode_1(cookie);
                String checkCode2 = checkCode2Pre.substring(0, 4);
                String result2 = String.valueOf(sendPost.sendPost(username, password, checkCode2, cookie));
                if (result2.contains("你输入的验证码错误，请您重新输入！"))
                    continue;
                else if (result2.contains("您的密码不正确，请您重新输入！")){
                    System.out.println("您的密码不正确，请您重新输入");
                }else
                    System.out.println(result2 + "登录成功: "+username+"验证码: "+checkCode2+" cookie："+cookie);
                break;
            }
        }
        SendGet sendGet = new SendGet();
        String resultClass = sendGet.sendGetClassTable(cookie);
        Document doc = Jsoup.parse(resultClass);
        Elements trs = doc.select(".odd");
        List<ClassInfo> list = new ArrayList<>();
        for (Element tr:trs){
            ClassInfo classInfo = new ClassInfo();
            Elements tds = tr.getElementsByTag("td");
            if (tds.size()>7){
                classInfo.setName(tds.get(2).text());
                classInfo.setTeacher(tds.get(7).text());
                classInfo.setStarAndEnd(tds.get(11).text());
                classInfo.setWeekDay(tds.get(12).text());
                classInfo.setAddress(tds.get(16).text());
                classInfo.setClassRoom(tds.get(17).text());
                list.add(classInfo);
                System.out.println("这是一个大行");
            }else {
                classInfo.setStarAndEnd(tds.get(0).text());
                classInfo.setWeekDay(tds.get(1).text());
                classInfo.setAddress(tds.get(5).text());
                classInfo.setClassRoom(tds.get(6).text());
                list.add(classInfo);
                System.out.println("这是一个小行");
            }
        }

        Map<String,Object> map = new HashMap<>();
        map.put("status","success");
        map.put("info",list);
     return map;
    }
}
