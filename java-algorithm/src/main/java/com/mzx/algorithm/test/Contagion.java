package com.mzx.algorithm.test;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦? 如梦之梦.
 * @create 2020-09-15 21:15 周二.
 */
public class Contagion {


    public static void main(String[] args) {

        // TODO: 第八题没有写出来.
        Contagion contagion = new Contagion();
        ConcurrentMap<Integer, Integer> map = new ConcurrentHashMap<>();
        // 第七天患病人数应该为5人.
        System.out.println("到现在为止,一共患病人数为: " + contagion.contagion(1, 7, map));

    }

    /**
     * 某种传染病第一天只有一个患者，
     * 前5天为潜伏期，不发作也不会传染人，
     * 治愈的那一天应该是不会传染的.
     * 第6天开始发作，从发作到治愈需要5天时间，期间每天传染3个人，
     * 求第N天共有多少患者
     * <p>
     * 现在需要考虑一个问题是,解决该问题不能再和上面一样, 解决该问题需要考虑数据结构问题.
     * <p>
     * 方法入参是: 1,expectNum
     *
     * @param expectNum 表示的是期望的天数。题中的n.
     * @param num       表示的是当前天数.
     * @return 返回值表示第num天一共有多少患者.
     */
    public int contagion(int num, int expectNum, Map<Integer, Integer> storage) {

        // TODO: 临界点处理是错误的.

        // 第一个map的键是当前天数.
        // 第二键值表示当前患病的人数.
        if (num <= 5) {

            if (num == expectNum) {

                return 1;

            }
            // 前五天不会增加传染病着.
            // 前五天内num也表示总的患病人数.
            // 这个hashMap存储的是在第n天患病的人数.
            // 这个num进去的值会非常大.
            // 前五天内将会造成重复数据.
            // 现在应该想办法解决.
            storage.put(1, 1);

        }


        // 应该首先遍历map. 先将患病天数>5的病人从数据结构中移除.
        final int day = num;
        // 新患病的人数.
        AtomicInteger newTotal = new AtomicInteger();
        AtomicInteger current = new AtomicInteger();
        storage.forEach((key, value) -> {

            // 其中key和value表示的是在第key天患病的人数.
            // 根据key开判断病人是否被治愈.
            if ((key + 10) < day) {

                // 这里表示可依据将第key天的病人从数据结构中移除掉.
                storage.remove(key);

            } else {

                // TODO这里判断有问题.
                // 新增加患病人数还需要考虑潜伏期的问题.
                // 如果不是话应该是增加患病人数.
                // 这个得病人数应该放在当前天数中.
                // 计算这个value还需要考虑key是否为潜伏期.
                // 如果key和当前天数差距小于5则说明不会患病.
                if (key > day - 5) {

                    // 表示现在换处于隐藏期.
                    System.out.println("计算. ");

                } else {

                    // 这里为什么会计算啊.
                    // 如果num=7那么noeTotal求出来的值就是3,但是实际上的值应该是1
                    if (key == day - 1) {

                        // 从第六天开始计算第七天应该重新弄个变量.


                    } else {

                        value = value * 3;
                        // 得病人数.
                        // 更新新的患病人数的时候换需要判断下当前天数是否在前五天之内.
                        if (day <= 5) {
                        } else {

                            newTotal.addAndGet(value);

                        }

                    }

                }

                // 以day作为key将会被覆盖.
                // 这里就不以day作为key重新放入了.
                //storage.put(day, value);

            }

        });

        // 这个put每次都需要put吗? 不是  nowTotal求的是有问题的.
        if (num > 5) {

            storage.put(num, newTotal.get());

        }

        // 正常退出条件.
        if (num == expectNum) {

            // 直接退出即可.
            Collection<Integer> values = storage.values();
            int total = 0;
            for (Integer value : values) {

                total += value;

            }

            // 第六天正常退出计算应该在
            return total;

        }

        // 第6天开始发作，从发作到治愈需要5天时间，期间每天传染3个人，
        // num表示第六天发病的总人数.
        // 那么第六天患病的人数应该是 num * 3 .
        // 但是现在还有一个问题就是: 需要考虑可能被治疗恢复的人.
        // 解决被治疗恢复的人.
        return this.contagion(++num, expectNum, storage);

    }

}
