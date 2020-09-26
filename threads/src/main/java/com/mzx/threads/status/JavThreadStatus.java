package com.mzx.threads.status;

import sun.awt.windows.ThemeReader;

import java.util.concurrent.TimeUnit;

/**
 * 利用Java命令jps查看当前线程的执行状态.
 *
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦? 如梦之梦.
 * @create 2020-09-17 00:18 周四.
 */
public class JavThreadStatus {

    public static void main(String[] args) {

        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();
        // 使用两个Blocked线程,一个占着锁不释放,一个等待锁.
        new Thread(new Blocked(),"BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();

    }


    static class TimeWaiting implements Runnable {

        @Override
        public void run() {

            while (true) {

                SleepUtils.second(100);

            }

        }

    }

    static class Waiting implements Runnable {

        @Override
        public void run() {

            while (true) {

                synchronized (Waiting.class) {

                    try {

                        // wait会释放锁.
                        // 当前线程已经是对Waiting获取了锁的,
                        // 调用了Waiting之后会释放对象锁的状态.
                        // 这个时候当前线程会在对象锁Waiting.class对象实例上进行等待.
                        Waiting.class.wait();

                    } catch (InterruptedException e) {

                        e.printStackTrace();

                    }

                }

            }

        }

    }

    static class Blocked implements Runnable {

        @Override
        public void run() {

            /**
             * 当前锁在当前实例中是不会被释放的.
             * 程序中是没有对该所进行释放的.
             */
            synchronized (Blocked.class) {

                while (true) {

                    SleepUtils.second(100);

                }

            }

        }

    }


    static class SleepUtils {

        public static final void second(long seconds) {

            try {

                TimeUnit.SECONDS.sleep(seconds);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }

    }

}
