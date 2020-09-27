package com.mzx.threads.threadpool;

/**
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-27 11:18 周日.
 */
public class ThreadPoolTest {

    public static void main(String[] args) {

        // 线程池工作线程数量.
        ThreadPool pool = new DefaultThreadPool(9);
        // 怎么查看当前运行的总的线程数量?
        pool.showWorkingThread();
        pool.execute(() -> {

            System.out.println(Thread.currentThread().getName());
            System.out.println("执行. ");

        });

    }

}
