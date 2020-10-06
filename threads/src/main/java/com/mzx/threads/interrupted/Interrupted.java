package com.mzx.threads.interrupted;

/**
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦?如梦之梦.
 * @create 2020-09-23 08:25 周三.
 */
public class Interrupted {

    public static void main(String[] args) throws InterruptedException {

        // 开启两个线程.
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        sleepThread.start();
        busyThread.start();

        System.out.println("主线程开始休眠. ");
        Thread.sleep(5000);
        System.out.println("主线程休眠完毕. ");
        // 主线程在休眠五秒之后对两个线程进行了中断操作.
        System.out.println("主线程对两个线程进行了中断操作. ");
        sleepThread.interrupt();
        busyThread.interrupt();

    }


    static class SleepRunner implements Runnable {

        @Override
        public void run() {

            boolean flag = true;

            while (flag) {

                try {

                    Thread.sleep(0);

                } catch (InterruptedException e) {

                    System.out.println("中断发生了. ");
                    flag = false;

                }

//                /*
//                 * 如果有别的线程对当前线程进行中断操作, 那么当前线程的中断标志位就是true.
//                 * */
//                if (Thread.currentThread().isInterrupted()) {
//
//                    // 如果当前线程有被别的线程进行中断. 猜测.
//                    System.out.println("Thread.currentThread().isInterrupted() 返回结果是true. ");
//                    flag = false;
//
//                }

            }

            System.out.println("SleepRunner 线程由于中断 线程结束. ");

        }

    }

    /**
     * 当前线程将会永远执行下去.
     * 并且当前线程也没有对中断进行检测， 猜测: 即使别的线程对当前线程间进行了中断,那么当前线程由于没有对其进行检测盒额响应，所以该线程将会永远执行
     * 下去.
     */
    static class BusyRunner implements Runnable {

        @Override
        public void run() {

            while (true) {
            }

        }

    }


}
