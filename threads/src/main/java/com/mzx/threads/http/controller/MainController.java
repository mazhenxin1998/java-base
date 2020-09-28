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

    @RequestMapping(value = "/t")
    public String main() {

        return "你好,Java";

    }

}
