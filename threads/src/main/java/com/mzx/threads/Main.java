package com.mzx.threads;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-28 08:31 周一.
 */
public class Main {

    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();
        // 当我们调用ReentrantLock中的lock方法.
        // 其内部调用的是Sync的一个内部类的额一个方法.
        // 但是这个Sync在初始化ReentrantLock的时候就已经进行了初始化,具体初始化可以看ReentrantLock的构造函数.
        lock.lock();
        try {

            // 尝试终止一个线程。

        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            lock.unlock();
        }


    }


}
