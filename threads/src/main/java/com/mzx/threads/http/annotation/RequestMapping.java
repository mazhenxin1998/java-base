package com.mzx.threads.http.annotation;

import java.lang.annotation.*;

/**
 * 要说明的是该注解必须添加在类上, 并且如果添加在类上,那么长度只能为1.
 * <p>
 * 长度为1表示 /main -----> 表示长度为1.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-28 22:32 周一.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMapping {

    String value();

    /**
     * 该属性是作用在方法上的表示参数的名字.
     * 如果没有参数,那么就不用进行这里的操作把.
     *
     * @return
     */
    String param() default "";

}
