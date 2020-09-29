package com.mzx.threads.http.controller;

import com.mzx.threads.http.annotation.RequestMapping;
import com.mzx.threads.http.annotation.RestController;

/**
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-29 23:36 周二.
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    /**
     * param是参数的变量名字.
     *
     * @return
     */
    @RequestMapping(value = "/get/message", param = "message")
    public String order(String message) {

        return message;

    }

}
