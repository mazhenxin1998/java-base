package com.mzx.threads.threadlocal;

/**
 * 分析工具.
 * <p>
 * 该工具可以用来分析一个方法执行时间的长短.
 *
 * 能否利用AOP切面编程计算出方法执行的耗时. ?
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-24 23:22 周四.
 */
public class Profiler {

    /**
     * final写重排序: 禁止final域的写重排序到构造函数之外.
     * final读重排序: 禁止对初次读对象引用与初次对对象包含的final域进行重排序.
     */
    private static final ThreadLocal<Long> TIME_END_START = new ThreadLocal<Long>() {

        @Override
        protected Long initialValue() {

            return System.currentTimeMillis();

        }

    };

    /**
     * 静态方法是不能别继承的,这个是当前类的一个方法,而不是子类的类的方法.
     * 添加final可能是为了防止指令重排序吧.
     */
    public static final void begin() {

        TIME_END_START.set(System.currentTimeMillis());

    }

    public static final Long end() {

        return System.currentTimeMillis() - TIME_END_START.get();

    }

    public static void main(String[] args) {

        try {

            begin();
            Thread.sleep(1000);
            System.out.println(end());

        } catch (InterruptedException e) {

            e.printStackTrace();

        }

    }

}
