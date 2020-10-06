package com.mzx.threads.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 利用读写锁实现的线程安全组件.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-10-05 09:00 周一.
 */
public class Cache {

    static Map<String, Object> cache = new HashMap<>();
    static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    static ReentrantReadWriteLock.ReadLock read = LOCK.readLock();
    static ReentrantReadWriteLock.WriteLock write = LOCK.writeLock();

    /**
     * 从缓存中读取一个数据.
     * <p>
     * 考虑到线程安全问题，所以说需要使用读锁来保证线程安全.
     *
     * @param key
     * @return
     */
    public static final Object get(String key) {

        read.lock();
        try {

            // 在锁内进行数据的获取.
            return cache.get(key);

        } catch (Exception e) {

            return null;

        } finally {

            read.unlock();

        }

    }

    /**
     * 向缓存中写入数据.
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {

        write.lock();
        try {

            cache.put(key, value);

        } finally {

            write.unlock();

        }

    }

}


