package com.mzx.threads.pool;

import com.mysql.jdbc.Connection;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在实际中获取不到的线程应该怎么办.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-25 23:44 周五.
 */
public class ConnectionTest {

    /**
     * 数据库连接池保证现在拥有10个连接.
     */
    static ConnectionPool pool = new ConnectionPool(10);

    /**
     * 保证了所有测试线程能够同时执行 模拟一瞬间并发效果.
     */
    static CountDownLatch start = new CountDownLatch(1);

    /**
     * main线程等待测试线程完毕之后才能结束执行.
     */
    static CountDownLatch end = null;

    public static void main(String[] args) throws InterruptedException {

        int threadCount = 50;
        end = new CountDownLatch(threadCount);
        // 表示一共有多少个测试线程.
        int count = 20;
        // 成功获取到连接的次数.
        AtomicInteger got = new AtomicInteger();
        // 没有成功获取到连接的数量.
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {

            new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread").start();

        }

        // 通知所有测试线程可以开始测试了.
        start.countDown();
        end.await();
        System.out.println("total invoke" + (threadCount * count));
        System.out.println("got connection " + got.get());
        System.out.println("notGot connection " + notGot.get());

    }

    static class ConnectionRunner implements Runnable {

        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {

            this.count = count;
            this.got = got;
            this.notGot = notGot;

        }


        @Override
        public void run() {

            try {

                // 等待所有测试线程都启动完成之后,所有测试线程一起并发执行.
                start.await();

            } catch (Exception e) {

                System.out.println(e);

            }

            while (count > 0) {

                try {

                    // 从连接池中获取连接, 如果在规定的时间内么有获取到, 那么就返回null.
                    Connection connection = pool.fetchConnection(1000);
                    // 现在就是统计一下20个线程获取到连接的数量和没有获取到连接的数量.
                    if (connection != null) {

                        try {

                            // 模拟执行.
                            connection.createStatement();
                            // 做了一层代理,该方式实际就是休眠0.1秒.
                            connection.commit();

                        } finally {

                            // 释放连接并且增加got的数量.
                            pool.releaseConnection(connection);
                            got.incrementAndGet();

                        }

                    } else {

                        notGot.incrementAndGet();

                    }

                } catch (Exception e) {

                    System.out.println(e);

                } finally {

                    count--;

                }

            }

            // 通知main线程 当前测试线程完成.
            end.countDown();

        }

    }


}
