package com.mzx.algorithm.test;

import java.util.HashSet;

/**
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦? 如梦之梦.
 * @date 2020/9/13 13:47.
 */
public class King {

    public static void main(String[] args) {

        King king = new King();
        king.kingProperty(1);

    }

    /**
     * 国王分财产。某国王临终前给儿子们分财产。他把财产分为若干份，
     * 然后给第一个儿子一份，再加上剩余财产的1/10；
     * 给第二个儿子两份，再加上剩余财产的1/10；……；
     * 给第i个儿子i份，再加上剩余财产的1/10。
     * 每个儿子都窃窃自喜。以为得到了父王的偏爱，
     * 孰不知国王是“一碗水端平”的。请用程序回答，
     * 老国王共有几个儿子？财产共分成了多少份？
     * 方法入参应该是0，其方法返回值就是分完之后应该剩余的财产.
     *
     * @param i 表示第i个儿子.
     * @return 方法返回值是: 分完之后剩余的财产吧?
     */
    public int kingProperty(int i) {

        // 这个题比上面一道题需要多做一份判断就是, 每个孩子所分的财产应该都是一样的。
        // set里面存放就是每个孩子所分的财产的类型, 如果说所有孩子所分得财产都是样的, 那么set的大小应该是1.
        // 还有一个总要的判断特征就是每次相邻的孩子的财产都应该相同,并且都为i.
        // 没每次当函数进来之后,我们都假设这个i是最后一个孩子, 并且并以假设的方式来保证所有孩子拿到的财产都是i分.
        HashSet<Integer> set = new HashSet<>();
        int total = 0;
        // 假设当前传进来的参数就是一共有的孩子个数.
        for (int j = i; j >= 1; j--) {

            // 判断.
            // 每次假设进来之后,不应该对最后一个孩子进行计算,最后一个孩子分之前国王就只有i分财产了,只能全部给最后一个孩子
            // 所以说, 只需要判断j-1之前的孩子的财产即可.
            // current表示第i个儿子发财产之前国王还有的财产数量.
            int current = 10 * (total / 9) + j;
            // 判断是不是9的倍数.
            if (current % 9 != 0) {

                // 只要有一个不是9的倍数,那么就break.
                // break之后下面的代码将不会执行，直接跳出整个for循环.
                break;

            }

            set.add(current - total);
            // 重新赋值total即可. : total表示的是分完财产之后所剩余的总的财产数.
            // 没循环一次这都应该重新计算.
            total = current;
            if (j == 1) {

                if (set.size() == 1) {

                    // 再次判断其中的哪一个是不是等于i
                    // 该判断是为了判断每个孩子所分的财产是一样的.
                    if (set.contains(i)) {

                        // 表示所有孩子分的财产都是一样的.
                        System.out.println("国王一共有: " + current + "份财产");
                        System.out.println("国王一共有: " + i + "个儿子");
                        return current;

                    }

                }

            }

        }

        set = null;
        return this.kingProperty(++i);

    }

}
