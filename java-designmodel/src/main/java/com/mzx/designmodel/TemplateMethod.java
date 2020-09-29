package com.mzx.designmodel;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 模板方法模式.
 * <p>
 * 模板方法模式就以ReentrantLock为例来讲.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-09-29 20:59 周二.
 */
public class TemplateMethod {

    public static void main(String[] args) throws Exception {

        ReentrantLock lock = new ReentrantLock();
        lock.lock();

    }


}
