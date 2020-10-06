package com.mzx.threads.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-10-06 23:36 周二.
 */
public class ConditionUseCase {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void conditionWait() {

        lock.lock();
        try {

            // 尝试进行等待.
            System.out.println("方法conditionWait等待中...");
            // 类似Object监视器方法进行等待/通知.
            //condition.await();
            // 模拟超时等待.
            // 当前返回值表示当前等待超时时间的剩余值.
            // 如果nanos<0 则表示超时了自动返回.
            // 就算其自动返回, 那么如果其获取不到锁那么仍然是暂停在这里.
            long nanos = condition.awaitNanos(2000);
            if (nanos < 0) {

                System.out.println("condition.awaitNanos方法超时了. ");
                return;

            }

            System.out.println("方法conditionWait等待结束.");

        } catch (Exception e) {


        } finally {

            lock.unlock();

        }

    }

    public void conditionSignal() {

        lock.lock();
        try {

            System.out.println("conditionSignal方法准备唤醒Wait方法了...");
            // 测试signal是否会释放锁.
            condition.signalAll();
            Thread.sleep(30000);
            System.out.println("conditionSignal方法唤醒Wait方法结束了.");

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            lock.unlock();

        }

    }

    public static void main(String[] args) throws InterruptedException {

        // 如果要测试的话,那么应该使用同一个对象.
        ConditionUseCase aCase = new ConditionUseCase();
        // 如果要测试的话,应该需要开启两个线程吧?
        Thread t1 = new Thread(() -> {

            // 调用第一个方法.
            aCase.conditionWait();

        });
        t1.start();
        Thread.sleep(0);
        new Thread(() -> {

            // 当前方法会阻塞当前线程.
            aCase.conditionSignal();

        }).start();

    }

}
