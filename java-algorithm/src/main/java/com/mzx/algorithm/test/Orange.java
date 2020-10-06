package com.mzx.algorithm.test;

import com.mzx.algorithm.util.NumberUtils;

/**
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦? 如梦之梦.
 * @date 2020/9/13 16:24.
 */
public class Orange {


    public static void main(String[] args) {

        Orange orange = new Orange();
        double[] doubles = orange.distributeOrange(120);
        System.out.println("------------------------");
        for (double aDouble : doubles) {

            System.out.println(aDouble);
        }

    }

    /**
     * 日本著名数学游戏专家中村义作教授提出这样一个问题：父亲将2520个桔子分给六个儿子。
     * 分完 后父亲说：
     * “老大将分给你的桔子的1/8给老二；
     * 老二拿到后连同原先的桔子分1/7给老三；
     * 老三拿到后连同原先的桔子分1/6给老四；
     * 老四拿到后连同原先的桔子分1/5给老五；
     * 老五拿到后连同原先的桔子分1/4给老六；
     * 老六拿到后连同原先的桔子分1/3给老大”
     * 结果大家手中的桔子正好一样多。
     * 问六兄弟原来手中各有多少桔子？
     * <p>
     * 方法入参: [420,420,420,420,420,420] 表示六个孩子分的橘子的数量是相等的,并且总的橘子个数为2520.
     *
     * @param i 该参数表示第i个孩子
     * @return
     */
    public double[] distributeOrange(int i) {

        // double是可以直接乘的。

        // 由老大橘子求出老二.
        // 0.13
        // 首先老大的财产必须是8的倍数,否则不进行。
        int power = 8;
        double temp = 0;
        if (i % 8 == 0) {

            // 将倍率-1. 倍率的分母.
            --power;
            // 表示老大的财产是可以分给老二的. 是八的倍数关系.
            // 老大分给老二的橘子也必须是整数。
            double oneGive = i / 8;
            // 老大分完之后自己还剩余的橘子.
            double oneResidue = i - oneGive;
            // for循环在里面进行的元婴是:  如果老大的 橘子不合法,那么久直接不进行循环.
            double preOrange = oneGive;
            boolean flag = false;
            // 该数组里面存放的是
            // 这个double数组初始化是有问题的.
            double[] resultOrange = new double[6];
            // 老大不应该存放.
            resultOrange[0] = oneResidue;
            for (int j = 2; j < 6; j++) {

                // i == 2 表示的是开始计算老二.
                int fp = power - 1;
                // 7/6
                double p = NumberUtils.divider(power, fp);
                // 从老二开始计算.
                // 这里计算的是老二剩余的. 老三剩余的 老四剩余的  老五剩余的
                // t表示老二剩余的.
                // TODO: 这个公式是没有问题的.
                // 思路是有问题的, 420* 谁    能没有小数值?
                // t求出的是老二的.
                double t = (420 / fp) * power - preOrange;
                // 每次求出来的这个t都应该有一个倍数关系.
                // 每个孩子原先之前有的橘子个数再加上上一个孩子所分的橘子个数应该有一个倍数关系.
                // 这里以老二为例: 老二的橘子个数再加上老大分给老二的应该是7的倍数关系. 也即是power关系.
                // tr表示的是老二在得到老大分的橘子之后所拥有的橘子个数.
                double tr = t + preOrange;
                if (tr % power != 0) {

                    // 直接退出循环.
                    break;

                }
                // 这里应该是老二给老三要分配的.
                // 更新每次传递的值.
                double x = tr / power;
                preOrange = x;
                // 对分完之后每个橘子进行保存.
                resultOrange[j - 1] = t;
                if (j == 5) {

                    // 应该先求出老六的值.


                    // temp表示老五给老六的橘子.
                    temp = x;
                    // 那么这个xxx就是老六的值.
                    // 直接在这里执行.
                    // 如果j=5表示当前是在计算老五的值.
                    // t 是老四没有给老五之前的值,也就是说要求的老五之前的值.
                    // 老大加上老六分给老大之前的值,应该是420;
                    // 现在只要判断.
                    // 这个是给老大的.
                    double xxxx = (630 / 3);
                    // 这个s应该就是老六原先应该有的值.
                    double s = 630 - preOrange;
                    resultOrange[5] = s;
                    // 这个000值应该是420 如果是420那么就应该是正确的.
                    double ooo = resultOrange[0] + xxxx;
                    if (ooo == 420) {

                        resultOrange[0] = i;
                        // 退出.
                        int k = 1;
                        for (double v : resultOrange) {

                            System.out.println("老" + k + "的橘子个数: " + v);
                            k++;

                        }

                        return resultOrange;

                    }

                }

                --power;

            }

            // 如果代码执行到这里能说明什么
            // 1. 第一中可能就就是上面break直接退出的,表示不合法.
//            if (flag) {
//
//                // 这里应该是正确的答案吧.
//                // 在这里对老六给老大分之后进行计算,并且将结果输出.
//                // 单独处理老六分橘子.
//                // 只要代码能运行到这里, 那么就说明老大到老五之间的橘子分配是合理的。
//                // 只要代码能运行到这里, 那么就只需要处理老六的。
//                // 我是能拿到老五的橘子的。 [4] 就是老五的橘子个数。
//                double fourOrange = resultOrange[4];
////                double ppp = NumberUtils.divider(1,4);
////				// rrr表示老六分给老大橘子的数量.
////				// 老五分给老六的橘子的个数 rrr。
//                // fourOrange表示的是老五原先有的橘子个数.
//                double rrr = (fourOrange + temp) / 4;
//                // 现在需要做的就是求出老五的橘子的个数以及求出老六给老大橘子的个数。
//                // f6表示的是老六的橘子个数.
//                double f6 = 630 - rrr;
//                // 赋值.
//                resultOrange[5] = f6;
//
//                // 现在应该根据老六橘子的个数求出老六分给老大的橘子的个数。
//                // f6ToOne表示的是老六分给老大的橘子的个数。
//                double f6ToOne = (f6 + rrr) / 3;
//                if ((f6 + rrr) % 3 == 0) {
//
//                    //表示是合法的值对于老六来说.
//                    // 换需要再次判断下老大的值是否是合法的。
//                    // 老大原先的。
//                    double o = resultOrange[0];
//                    // 这里应该是420
//                    double dd = o + f6ToOne;
//                    if (dd == 420) {
//
//                        // 没有两个方法相互依赖于返回值.
//                        // 所以说这个数组可以
//                        resultOrange[0] = i;
//                        return resultOrange;
//
//                    }
//
//                }
//
//            }

        }

        // 如果不满足8的倍数关系,那么久往下面走。
        i = i + 8;
        return this.distributeOrange(i);

    }


}
