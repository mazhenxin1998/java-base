package com.mzx.threads.http.controller;

import com.mzx.threads.http.annotation.RequestMapping;
import com.mzx.threads.http.annotation.RestController;

/**
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-28 00:12 周一.
 */
@RestController
@RequestMapping(value = "/main")
public class MainController {

    @RequestMapping(value = "/t" ,param = "name")
    public String main(String name) {

        return name;

    }

    @RequestMapping(value = "/java", param = "n")
    public String java(String n){

        return n;

    }

}
