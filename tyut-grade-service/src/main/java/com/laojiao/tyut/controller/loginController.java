package com.laojiao.tyut.controller;

import org.jsoup.Jsoup;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by SuperJohn on 2017/8/24.
 */
@RestController
@RequestMapping("/login")
public class loginController {
    @PostMapping("/")
    public void login(@RequestParam("username")String username,
                      @RequestParam("password")String password,
                      HttpSession session) throws IOException {
        String cookiePre = String.valueOf(Jsoup.connect("http://202.207.247.44:8089").execute().cookies());
        String cookie = cookiePre.replace("{", "").replace("}", "");
        session.setAttribute(username,cookie);
    }
}
