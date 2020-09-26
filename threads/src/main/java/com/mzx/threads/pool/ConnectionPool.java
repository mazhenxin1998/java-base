package com.mzx.threads.pool;

import com.mysql.jdbc.Connection;

import java.util.LinkedList;

/**
 * 一个简单数据库连接池示例.
 * 使用等待超时模式.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-25 23:11 周五.
 */
public class ConnectionPool {

    /**
     * 利用volatile的内存屏障.
     */
    private volatile int visuality = 0;

    /**
     * 存放的是Sql连接.
     */
    private volatile LinkedList<Connection> pool = new LinkedList<>();

    /**
     * 初始化连接池中的连接.
     *
     * @param initialSize 要初始化连接的数量.
     */
    public ConnectionPool(int initialSize) {

        if (initialSize > 0) {

            for (int i = 0; i < initialSize; i++) {

                pool.addLast(ConnectionDriver.createConnection());

            }

        }

    }

    /**
     * 释放参数中指定的连接从连接池中.
     * <p>
     * 获取连接时从连接池中取出一个连接,释放连接是将一个链接归还到池中.
     *
     * @param connection
     */
    public void releaseConnection(Connection connection) {

        if (connection != null) {

            // wait和notify、notifyAll 必须和synchronized成对出现才不会报错.
            // 否则将会抛出异常java.lang.IllegalMonitorStateException.
            synchronized (pool) {

                // 区域网
                pool.addLast(connection);
                // 每次链接释放之后需要进行通知, 这样其他消费者能够感知到连接池中已经归还了一个链接.
                // 现在不知道在外面进行通知有问题没？
                pool.notifyAll();

            }

        }

    }

    /**
     * 在规定时间内获取连接.
     * 如果在规定时间内没有获取到连接, 那么就返回null.
     * <p>
     * 能不能进一步进行改进: 这里是方法只要一进来就锁方法,能不能将锁的粒度降低一些?
     *
     * @param mills 小于0表示没有时间限制,大于0表示在该规定时间内获取连接,如果获取不到就直接返回null.
     * @return
     */
    public Connection fetchConnection(long mills) throws InterruptedException {

        // TODO: 2020/9/25  上来就直接加锁?
        synchronized (pool) {

            // 表示没有时间限制.
            if (mills <= 0) {

                while (pool.isEmpty()) {

                    pool.wait();

                }

                // 移除头节点的连接并且返回.
                return pool.removeFirst();

            }

            // 表示未来截止时间.
            long future = System.currentTimeMillis() + mills;
            // 余额.
            long remaining = mills;
            while (pool.isEmpty() && remaining > 0) {

                // 如果池中没有可用连接并且剩余可用时间大于0.
                pool.wait(remaining);
                // 如果remaining小于0那么就说明规定的时间已经到了.
                remaining = future - System.currentTimeMillis();

            }

            Connection result = null;
            return pool.isEmpty() ? null : pool.removeFirst();

        }

    }

}
