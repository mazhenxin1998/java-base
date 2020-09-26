package com.mzx.threads.threadlocal;

import com.sun.media.sound.SF2Sample;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.lang.ref.SoftReference;
import java.util.LinkedList;

/**
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦?如梦之梦.
 * @create 2020-09-23 14:15 周三.
 */
public class ThreadLocalTest {

    public static final ThreadLocal<Integer> local = new ThreadLocal<>();

    public static void main(String[] args) {

        // 声明一个软引用对象.
        // sf变量指向的对象是强引用.
        // 声明这个软引用对象的时候已经给其赋值了的 值就是一个字节数组.
        SoftReference<byte[]> sf = new SoftReference<>(new byte[10 * 1024 * 1024]);

    }

}
