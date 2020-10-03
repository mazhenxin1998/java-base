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

        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        try {


        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            lock.unlock();
        }

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
