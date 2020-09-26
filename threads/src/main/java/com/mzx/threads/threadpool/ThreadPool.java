package com.mzx.threads.threadpool;

/**
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-26 23:57 周六.
 */
public interface ThreadPool<Job extends Runnable> {

    /**
     * 执行一个Job,这个Job需要实现Runnable接口.
     *
     * @param job
     */
    void execute(Job job);

    /**
     * 关闭线程池.
     */
    void shutdown();

    /**
     * 增加工作者线程.
     *
     * @param num
     */
    void addWorkers(int num);

    /**
     * 减少工作者线程.
     *
     * @param num
     */
    void removeWorker(int num);

    /**
     * 或强当前正在等待线程执行的任务.
     *
     * @return
     */
    int getJobSize();

}
