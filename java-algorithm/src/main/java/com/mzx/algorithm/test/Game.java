package com.mzx.algorithm.test;

import com.sun.istack.internal.NotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author ZhenXinMa.
 * @slogan 滴水穿石, 不是力量大, 而是功夫深.
 * @date 2020/9/10 16:19.
 */
public class Game {

    /**
     * 运动会开了N天，一共发出金牌M枚。
     * 第一天发金牌1枚加剩下的七分之一枚，
     * 第二天发金牌2枚加剩下的七分之一枚，
     * 第3天发金牌3枚加剩下的七分之一枚，
     * 第n天发奖牌之前还有n枚奖牌》
     * 以后每天都照此办理。到了第N天刚好还有金牌N枚，
     * 到此金牌全部发完。编程求N和M。
     * 调用该方法的时候只传一个参数就是表示该运动会是第几天.
     *
     * @return 返回值应该跟奖牌是挂钩的, 否则入参不能确定.  返回值这样来确定: 表示第num天结束之后剩余奖牌的数量.
     * 也就是说当输入参数是运动会最后一天的话返回的奖牌数应该是0.
     */
    public static void main(String[] args) {

        Game game = new Game();
        System.out.println("运动会总奖章数: " + game.game(1L));
    }

    public static double divider(@NotNull int fz, @NotNull int fm) {

        DecimalFormat df = new DecimalFormat("0.00");
        // 现在返回的这个值有一个前提条件: 那就是必须是整数.
        // 向高位对齐.
        return Double.parseDouble(df.format((double) fz / (fm)));

    }

    /**
     * 方法入参: 表示的是第几天,返回值表示当前奖牌发完之后所剩下的奖牌数量.  所以说入参只能是第一天.
     *
     * @param num 1. 第1天. 2. num是假设值
     * @return 返回值表示第一天操作之后所剩余的奖牌数量. 、、 6
     */
    public double game(double num) {

        double n = num;
        // 方法每次执行的时候都应该判断是否符合7的倍数的条件.
        // 由于题中给出了第num天的奖牌数为num.
        // m表示第m天发完奖牌之后的总奖牌数,  当每次方法由递归进入之后,m的值都会变成0.
        // 假如现在是第六天: 那么这个m是不是就0;
        double m = 0;
        // 表示第一天发的奖牌数.
        int count = 1;
        // total表示当前次反向求出来的总的奖牌数.
        int total = 0;

        // 现在是2天.
        // 函数退出条件 : 必须保证当前天数以及当前天数前面的天数的奖牌数必须是合格的。
        // return:  条件是从num天数往前判断,直到第一天.
        // 现在假如6天.
        for (double i = num; i >= 1; i--) {


            // 针对每次假设进行求解.
            // 只要方法进来了,那么就假设当前的num是符合预期值的.
            // 现在要求出前一天的表达式.
            // preM表示前一天的奖牌数.
            int v = (int) divider(7, 6);
            // TODO: 现在v*m是返回值必须是整数,那样才对该整数进行求余.
            // 现在要保证的是乘了之后必须是一个整数值.
            // TODO: 怎么保证求和之后是一个整数. ?
            // current表示当前次数(后的)的奖牌数。 这个应该和total类似吧. m不能用total.
            // m表示后,current表示当前次数前-----> 总奖章数.
            int current = (int) ((v * m) + num);
//            if (preM == num) {
//
//                // 表示直接退出.
//                System.out.println("循环结束 结果是: " + num);
//                return num;
//
//            }

//            double preM = (((7 / 6) + 1) * num) - 1;
//            Long preM = ((7 / 6) * num - 1);
            // 现在我要保证前一天的奖牌数和7的倍数有一个关系.
            // (preM - (num - 1)) % 7 == 0
            if ((current + count) % 7 == 0) {

                // 此次是合格的.
                m = current;
                // 如果前一天奖牌数余数为0,那么一直运行.
                if (n == 1) {

                    // 如果 num==1并且余数为0,那就表示当前参数假设成立.
                    System.out.println("运动会总天数:  " + num);
                    return m;

                }

                n--;

            }

            count++;

        }

        // 如果第num天数不符合题意呢?
        // 那就看看第num+1天的天数是不是符合题意的.
        return this.game(++num);

    }

}
