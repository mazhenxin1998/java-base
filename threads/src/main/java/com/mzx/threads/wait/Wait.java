package com.mzx.threads.wait;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * wait方法使用详情.
 * <p>
 * 1. wait必须和synchronized同时出现,否则, 将会抛出异常.
 * 2. wait调用之后将会将当前线程的状态变为等待状态.
 * 3. 只能由对象锁调用wait,并且该对象锁在当前线程中调用了wait,那么当前线程将会被放在对象锁的等待队列中.
 * 4. 等待队列里面的线程只能由当前对象锁进行调用notify才会激活.
 *
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦?如梦之梦.
 * @create 2020-09-23 08:34 周三.
 */
public class Wait {

    public static Object lock = new Object();
    public static boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

//        new Thread(new W(), "WaitThread").start();
//        Thread.sleep(1000);
//        new Thread(new N(), "NotifyAllThread").start();
        new Thread(new Rl()).start();


    }

    static class Rl implements Runnable {

        ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {

            lock.lock();
            try {

                // 现在有一个问题就是: 怎么让非synchronized锁来实现进入等待队列中呢?
                // 进入等待队列中.
                // 但是其仍然和wait是不相同的.
                // 在实现Lock接口的所有的锁的实现中,可以通过LockSupport提供的part和unPark进行对wait和notify的效果等价替换.
                LockSupport.park();
                System.out.println("LockSupport.park() 语句下面的输出语句执行了");


            } catch (Exception e) {

                e.printStackTrace();
            } finally {

                lock.unlock();
            }


        }

    }

    static class W implements Runnable {

        @Override
        public void run() {

            synchronized (lock) {

                while (flag) {

                    try {

                        // 现在要判断是调用哪个wait方法进入等待状态?
                        System.out.println(Thread.currentThread().getName() + " 时间: " +
                                new SimpleDateFormat("HH:mm:ss").format(new Date()));
                        // 先尝试调用lock的wait方法.
                        // 不管使用当前对象还是对象锁的wait方法, 其实效果都是一样的, 因为其底层都是用一个JNI方法.
                        // 其都会导致当前线程进行等待.
                        // 调用对象锁的那个wait方法可以将当前线程处于等待状态.
                        // 建议使用对象锁的wait方法,这样可以使代码看的简洁明了.
                        // lock.wait() 的语义是将当前线程放置到lock这个对象的等待队列中.
//                        lock.wait();
                        // 这个wait方法是当前类的wait方法.
                        // 会导致当前线程进行等待.
                        // 不能直接用wait?  如果直接使用wait那么就将不会进入等待队列中.
                        wait();
                        // 如果想让线程进入等待队列,那么就必须使用当前线程获取到的对象锁的实例进行wait，才能让当前线程进入等待,否则将会一直执行.
                        System.out.println("lock.wait() 语句后面的语句执行了. ");

                    } catch (Exception e) {
                    }

                }

                // 如果当前线程被其他线程唤醒通过调用对象锁的lock.notifyAll().
                // 那么当前线程就会去重新竞争锁.
                // 所以说如果调用notifyAll()的线程没有释放锁,那么当前线程也是获取不到锁的.
                System.out.println("线程: " + Thread.currentThread().getId() + "条件满足. ");
                System.out.println(Thread.currentThread() + " 时间  : " + new SimpleDateFormat("HH:mm:ss")
                        .format(new Date()));

            }

        }

    }

    static class N implements Runnable {

        @Override
        public void run() {

            synchronized (lock) {

                System.out.println(Thread.currentThread() + " 时间  : " + new SimpleDateFormat("HH:mm:ss")
                        .format(new Date()));
                lock.notifyAll();
                flag = false;
                // 保证效果可见性
                try {

                    Thread.sleep(5000);

                } catch (InterruptedException e) {
                }

            }

            synchronized (lock) {

                System.out.println(Thread.currentThread() + " 时间  : " + new SimpleDateFormat("HH:mm:ss")
                        .format(new Date()));
                System.out.println("线程: " + Thread.currentThread().getName() + "第二次获取锁");


            }


        }

    }

}
