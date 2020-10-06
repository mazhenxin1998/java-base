package com.mzx.threads.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-10-03 08:26 周六.
 */
public class LockTest {

    private final ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) {

        int x = 0;
        // 读状态加一，那么就是以下面的方式进行加一?
        // 那么为什么读一次就直接到65536了?
        //System.out.println(x + 0x00010000);
        // 1左移16.
        // 1<<16 表示的就是1 * 2 的16次幂.
        System.out.println(1 << 16);


    }

    public void lock1() {

        lock.lock();
        try {

            // 测试lock2需要在开启一个线程.
            new Thread(() -> {

                lock2();

            }).start();
            Thread.sleep(1000000);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            lock.unlock();
        }


    }

    public void lock2() {

        lock.lock();
        try {


        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            lock.unlock();
        }

    }


}
