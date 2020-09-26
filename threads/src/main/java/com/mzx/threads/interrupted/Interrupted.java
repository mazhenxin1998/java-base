package com.mzx.threads.interrupted;

/**
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦?如梦之梦.
 * @create 2020-09-23 08:25 周三.
 */
public class Interrupted {

    public static void main(String[] args) throws InterruptedException {

        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        sleepThread.start();
        busyThread.start();
        Thread.sleep(5000);
        System.out.println(System.currentTimeMillis());
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        Thread.sleep(5000);

    }


    static class SleepRunner implements Runnable {

        @Override
        public void run() {

            while (true) {

                try {

                    Thread.sleep(10);

                } catch (InterruptedException e) {

                    // 如果有别的线程来调用当前线程的interrupt()方法
                    // 那么就会触发当前异常.
                    System.out.println(System.currentTimeMillis());
                    System.out.println("SleepRunner InterruptedException 异常发生了.  ");

                }

            }

        }

    }

    static class BusyRunner implements Runnable {

        @Override
        public void run() {

            while (true) { }

        }

    }


}
