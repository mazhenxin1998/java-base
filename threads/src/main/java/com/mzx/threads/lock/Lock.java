package com.mzx.threads.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-10-03 08:11 周六.
 */
public class Lock {

    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock(true);
        lock.lock();
        // 先模拟第一个获取锁的线程暂停.
        try {

            Thread.sleep(1000000);
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            lock.unlock();
        }


    }

    public void noFairLock() {

        // 默认是非公平锁.
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {


        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            lock.unlock();
        }

    }

}
