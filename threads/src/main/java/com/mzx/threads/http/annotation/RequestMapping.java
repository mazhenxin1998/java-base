package com.mzx.threads.http.annotation;

import java.lang.annotation.*;

/**
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-28 22:32 周一.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value();

}
