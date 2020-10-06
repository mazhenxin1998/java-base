package com.mzx.threads.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁基本使用.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-10-05 08:58 周一.
 */
public class ReadWriteLock {

    public static void main(String[] args) {

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        // 对读锁加锁.
        lock.readLock().lock();
        // 对写锁加锁.
        lock.writeLock().lock();
        // TODO: 2020/10/6 读写锁源码分析.


    }

}
