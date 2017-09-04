package com.tyut.eureka.controller;

import com.tyut.eureka.core.pojo.HelpStudy;
import com.tyut.eureka.core.util.SendGet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SuperJohn on 2017/8/20.
 */
@RestController
@RequestMapping("/help")
public class HelpController {
    //固定岗
    @GetMapping("/list1/{page}")
    public Map<String, Object> list1(@PathVariable("page")Integer page) throws IOException {
        SendGet sendGet = new SendGet();
        List<HelpStudy> list = sendGet.getHelpList("http://qgzx.tyut.edu.cn/sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jx=B8cmjbKnnOZkXcZog7FtLhNJ+LQ+gy78&page="+page);
        Map<String,Object> map = new HashMap<>();
        map.put("status","success");
        map.put("type","固定岗");
        map.put("info",list);
        return map;
    }
    //临时岗
    @GetMapping("/list2/{page}")
    public Map<String, Object> list2(@PathVariable("page")Integer page) throws IOException {
        SendGet sendGet = new SendGet();
        List<HelpStudy> list = sendGet.getHelpList("http://qgzx.tyut.edu.cn/sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jx=nnpmEefYrudX/AfSe+aApv7gIcFy0len&page="+page);
        Map<String,Object> map = new HashMap<>();
        map.put("status","success");
        map.put("type","临时岗");
        map.put("info",list);
        return map;
    }
    //专业技术岗
    @GetMapping("/list3/{page}")
    public Map<String,Object> list3(@PathVariable("page")Integer page)throws IOException{
        SendGet sendGet = new SendGet();
        List<HelpStudy> list = sendGet.getHelpList("http://qgzx.tyut.edu.cn/sec_zpzx.jsp?dt=nnpmEefYrudX/AfSe+aApv7gIcFy0len&jx=9mh5186NSY7s5s8lrFQS7S4oJZ0WUZPq&page="+page);
        Map<String,Object> map = new HashMap<>();
        map.put("status","success");
        map.put("type","专业技术岗");
        map.put("info",list);
        return map;
    }
    //校外岗
    @GetMapping("/list4/{page}")
    public Map<String,Object> list4(@PathVariable("page")Integer page)throws IOException{
        SendGet sendGet = new SendGet();
        List<HelpStudy> list = sendGet.getHelpList("http://qgzx.tyut.edu.cn/sec_zpzx.jsp?dt=9mh5186NSY7s5s8lrFQS7S4oJZ0WUZPq&jx=nnpmEefYrudX/AfSe+aApv7gIcFy0len&page="+page);
        Map<String,Object> map = new HashMap<>();
        map.put("status","success");
        map.put("type","校外岗");
        map.put("info",list);
        return map;
    }
    @GetMapping("/info/{id}")
    public String info1(@PathVariable("id")String id) throws IOException {
        SendGet sendGet = new SendGet();
        String result = sendGet.getHelpInfo(id);
        return result;
    }
}
