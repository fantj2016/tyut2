package com.tyut.eureka.core.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by SuperJohn on 2017/8/19.
 */
public class ServiceConstant {
    public static Integer STATE_LOGIN_FAIL = 0;   //登录失败
    public static Integer STATE_LOGIN_SUCCESS = 1;    //登陆成功
    public static Integer STATE_SELECT_FAIL= 2;   //查询成功
    public static Integer STATE_SELECT_SUCCESS= 3;    //查询失败
    /**状态 Int-String 映射*/
    public static Map<Integer,String> STATE_MAP;
    static {
        STATE_MAP = new HashMap<Integer,String>();
        STATE_MAP.put(STATE_LOGIN_FAIL,"login file");
        STATE_MAP.put(STATE_LOGIN_SUCCESS,"login success");
        STATE_MAP.put(STATE_SELECT_FAIL,"select file");
        STATE_MAP.put(STATE_LOGIN_SUCCESS,"select success");
    }
}
