package com.mzx.threads.test;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦? 如梦之梦.
 * @create 2020-09-16 23:25 周三.
 */
public class Jmx {

    /**
     * 查看启动一个main入口类,有几个线程一起执行.
     *
     * @param args
     */
    public static void main(String[] args) {

        /* 没有获取monitor和synchronizer的信息状况.
         * --------------------------------------------------------
         * [6]Monitor Ctrl-Break
         * [5]Attach Listener
         * [4]Signal Dispatcher
         * [3]Finalizer
         * [2]Reference Handler
         * [1]main.
         * --------------------------------------------------------
         * */

        // 获取Java线程管理MXBean.
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的monitor和synchronizer信息.
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true,
                true);
        for (ThreadInfo threadInfo : threadInfos) {

            System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());

        }

    }


}
