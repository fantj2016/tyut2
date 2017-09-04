package com.laojiao.eureka;

import com.tyut.eureka.core.pojo.Grade;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by SuperJohn on 2017/8/19.
 */
@FeignClient("tyut-grade-service")
@RequestMapping("/grade")
public interface GradeClient {
    /**
     * 根据id查grade表
     * @return Grade
     */
    @GetMapping("/select")
     Grade selectById()throws Exception;
}
