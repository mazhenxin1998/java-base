package com.mzx.threads.threadpool;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-27 00:00 周日.
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {

    /**
     * 线程最大工作线程的最大限制.
     */
    private static final int MAX_WORKER_NUMBERS = 10;
    /**
     * 当前线程池默认工作线程数量.
     */
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    /**
     * 当前线程池默认的最小工作线程数量.
     */
    private static final int MIN_WORKER_NUMBERS = 1;
    /**
     * 工作列表: 双向队列,存放着等待执行的任务.
     */
    private final LinkedList<Job> jobs = new LinkedList<>();
    /**
     * 正在工作者的列表.
     * 工作者线程集合.
     */
    private final List<Worker> workers = new LinkedList<>();

    private int workerNum = DEFAULT_WORKER_NUMBERS;

    /**
     * 线程编号生成.
     */
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool() {

        this.initializeWorkers(DEFAULT_WORKER_NUMBERS);

    }

    public DefaultThreadPool(int num) {

        // 在三元运算符中使用三元运算符.
        workerNum = num > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : num < MIN_WORKER_NUMBERS ? MIN_WORKER_NUMBERS : num;
        this.initializeWorkers(workerNum);

    }

    private void initializeWorkers(int num) {

        for (int i = 0; i < num; i++) {

            Worker worker = new Worker();
            workers.add(worker);
            new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet()).start();

        }

    }

    @Override
    public void execute(Job job) {

        if (job != null) {

            synchronized (jobs) {

                jobs.addLast(job);
                // 添加一个工作,然后通知一个工作者线程进行工作.
                // 这里没有通知所有工作者线程是为了避免线程竞争造成的额外的线程损耗.
                // 后面工作者线程在监听到工做队列中没有任务的时候将会进行等待,和这里的通知正好匹配.
                jobs.notify();

            }

        }

    }

    @Override
    public void shutdown() {

        workers.forEach(worker -> {

            worker.shutdown();

        });

    }

    @Override
    public void addWorkers(int num) {

        // 此次要增加的工作者线程数量.
        int number = num + this.workerNum > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : MAX_WORKER_NUMBERS
                - this.workerNum;
        synchronized (jobs) {

            // TODO: 2020/9/27  这个和带参构造器重复了.
            initializeWorkers(number);
            this.workerNum = number;

        }

    }

    @Override
    public void removeWorker(int num) {

        // 移除num个工作线程不需要锁定判断条件. 应该是不确定.
        if (num >= this.workerNum) {

            throw new IllegalArgumentException("byyond workNum");

        }

        // 按照给定的数量停止线程.
        synchronized (jobs) {

            int count = 0;
            while (count < num) {

                Worker worker = workers.get(count);
                // 先从队列中移除,然后在停止.
                if (workers.remove(worker)) {

                    worker.shutdown();
                    count++;

                }

            }

            this.workerNum -= count;

        }

    }

    @Override
    public int getJobSize() {

        return jobs.size();

    }

    // TODO: 2020/9/27 明天早上完成工作者线程完成任务的功能.

    class Worker implements Runnable {

        /**
         * 在上面初始化的时候new了num个Worker,也就是说每个工作者线程都拥有自己的running属性.
         * 这里使用volatile进行对running进行修饰,是因为在前面shutdown方法中要对每个工作者线程的工作状态进行改变.
         * 如果不使用volatile进行修饰, 那么将会出现线程安全问题. 下面是为什么会出现线程安全问题的描述.
         * 假设当前工作者线程正在执行一个任务当时快要执行完了的时候,Main线程已经对整个线程池进行关闭操作,但是这个时候你要是没有使用volatile
         * 对running进行修饰,Main线程对当前工作者线程的running的修改可能还没有被CPU刷会到主内存中,对当前工作者线程的running的修改之后的
         * 值还存放在Main线程的工作线程内存区域. 而这个时候,当前工作者线程正好执行完一个任务,当再去判断while循环条件的时候发现仍然是可执行状态,
         * 那么当前工作者线程将会默认的认为线程池还是未关闭状态,那么其将会继续从任务队列中取出任务进行执行.
         */
        private volatile boolean running = true;

        @Override
        public void run() {

        }

        public void shutdown() {

            // 停掉当前工作者线程,使线程优雅的退出.
            this.running = false;

        }

    }

}
