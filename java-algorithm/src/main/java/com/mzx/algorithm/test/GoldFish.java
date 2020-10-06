package com.mzx.algorithm.test;

/**
 * 金鱼问题:
 * 出售金鱼问题：
 * 第一次卖出全部金鱼的一半加二分之一条金鱼；
 * 第二次卖出乘余金鱼的三分之一加三分之一条金鱼；
 * 第三次卖出剩余金鱼的四分之一加四分之一条金鱼；
 * 第四次卖出剩余金鱼的五分之一加五分之一条金鱼；
 * 现在还剩下11条金鱼，在出售金鱼时不能把金鱼切开或者有任何破损的。
 * 问这鱼缸里原有多少条金鱼？
 *
 * @author ZhenXinMa
 * @date 2020/9/7 13:52
 */
public class GoldFish {

    public static int c = 1;

    public static void main(String[] args) {

        GoldFish fish = new GoldFish();
//        System.out.println(fish.sale(11, 4));
//        System.out.println(fish.cart(8L, 6L, 0L));
//        System.out.println(fish.monkey(9L, 0L));
        System.out.println(fish.book(5L, 3L));
//        System.out.println(fish.games(1L));

    }


    /**
     * 在第一次卖了之后还剩余11条. 现在首先需要想明白两个参数是什么意思.
     *
     * @param count count表示第num次卖完之后剩余的鱼.
     * @param num
     * @return
     */
    public float sale(float count, float num) {

        if (num == 0) {

            return count;

        }

        num++;
        float total = 0;
//        total = count * (num + 1) - 1;
        total = (count + (1 / num)) / (1 - (1 / num));
//        num = num - 1;
//        num--;
        num -= 2;
        // 第三次卖完之后应该剩余.
        return this.sale(total, num);

    }

    /**
     * 公路汽车问题: 某路公共汽车，总共有八站，
     * 从一号站发轩时车上已有n位乘客.
     * 1. 到了第二站先下一半乘客，再上来了六位乘客.
     * 2. 到了第三站也先下一半乘客，再上来了五位乘客.
     * 3. 以后每到一站都先下车上已有的一半乘客，再上来了乘客比前一站少一个
     * 4. 到了终点站车上还有乘客六人，问发车时车上的乘客有多少.
     * <p>
     * 倒着来将会有一个问题是: 当前车站上车的人数不确定.
     * <p>
     * 还需要考虑一个问题: 就是第一站的问题.
     *
     * @param num   当前是第几车站.
     * @param size  当前车站上有几个人数.
     * @param getOn 当前车站上车的人数. 默认为0;
     * @return 当前车站
     */
    public Long cart(Long num, Long size, Long getOn) {

        // 先定义递归函数的出口.
        if (num == 2) {

            // num = 8的时候 size为6
            // 函数调用的时候应该传递参数为
            // 第八站上车的人数为0.
            return size;

        }

        // 上车的时候是从第七站开始的》
        // 现在的size是第八站的结果.
        // 需要由第八站推出第七站.
        getOn++;

        // 先递归获取到上车的人数.
        // 执行到站之后的操作.
        // 1. 先下车.
        // 因为我是倒着来的,所以说每次应该乘. 还需要考虑一个问题是: 是先乘还是先加.
        size -= getOn;
        size = size << 1;
        num--;
        return this.cart(num, size, getOn);

    }

    /**
     * 猴子吃桃。有一群猴子摘来了一批桃子，
     * 假设总共摘了n个桃子.
     * 猴王规定每天只准吃一半加一只（即第二天吃剩下的一半加一只，以此类推），
     * 举例:
     * 1. 第一天吃摘了n个桃子的一半再加一个.
     * 2. 第二天吃第一天剩余的一半再加一个.
     * ...
     * 第九天正好吃完，问猴子们摘来了多少桃子？
     * 函数调用的入参: 9 0 表示第九天吃完之后剩余0.
     *
     * @param num  第几天.
     * @param size 当前天数吃完之后剩余的桃树。
     * @return
     */
    public Long monkey(Long num, Long size) {

        // 先定义函数出口.
        if (num == 1) {

            // 还需要再次处理,不能再和上次一样直接返回.
            size++;
            size = size << 1;
            return size;
        }

        // 开始进行处理.
        // 现在是由第九天开始计算第八天.
        size++;
        size = size << 1;
        return this.monkey(--num, size);

    }

    /**
     * 小华读书。
     * 第一天读了全书的一半加二页，
     * 第二天读了剩下的一半加二页，
     * 以后天天如此……，
     * 第六天读完了最后的三页，问全书有多少钱页？
     * 调用该方法传的参数是； 6 3
     *
     * @param num  第几天.
     * @param size 当前第num天读完之后还剩余多少页.
     * @return
     */
    public Long book(Long num, Long size) {

        // size表示第num-1天读完之后还剩余的页数.

        if (num == 1) {

            size = size + 2;
            size = size << 1;
            return size;

        }

        // 第五天读完之后就剩下三页.
        // 调用该函数传的参数可以是: 5 3
        // 由第五天求解出第四天的过程.
        size = size + 2;
        size = size << 1;
        return this.book(--num, size);

    }

    /**
     * 运动会开了N天，一共发出金牌M枚。
     * 第一天发金牌1枚加剩下的七分之一枚，
     * 第二天发金牌2枚加剩下的七分之一枚，
     * 第3天发金牌3枚加剩下的七分之一枚，
     * 以后每天都照此办理。到了第N天刚好还有金牌N枚，
     * 到此金牌全部发完。编程求N和M。
     * 调用该方法的时候只传一个参数就是表示该运动会是第几天.
     *
     * @param num num表示当前运动会使第几天.
     * @return 返回值应该跟奖牌是挂钩的, 否则入参不能确定.  返回值这样来确定: 表示第num天结束之后剩余奖牌的数量
     * 也就是说当输入参数是运动会最后一天的话返回的奖牌数应该是0.
     */
    public Long games(Long num) {

        // TODO: 应该还需要判断一下num==1的值. 不知道这里需不要进行判断.
        // 如果是第一天了?
        if (num <= 0) {

            return -1L;

        }

        // 第n天如果把奖牌发完了之后,奖牌个数为0.
        // TODO: 不能明确该方法调用时候的入参.
        // 到了第n天刚好还有n个金牌: 表示的是第n天还没开始发放的金牌的数量.
        // 这个n天是运动会开的总天数.
        // 需要注意的是这个size应该怎么获取到?
        // 这个判断条件是不能确定的.
        // 如果在num天正好结束,那么在该天应该正好有num个金牌.
        if (num.equals(this.games(--num))) {

            // 方法的出口.
            // 不知道入口参数...
            // 现在是第n天并且奖牌数为n个.
            // 这个范湖值表示的运动会开了几天.
            return num;

        } else {

            // 现在假设num是已知的.
            // 由第num天推num-1天的金牌数量.
            // 如果不能天数和奖牌数不能确定那么就继续进行递归.
            // 如果计算第一天开始之前就有多少奖牌,那么就应该获取到第二天的奖牌数量.
            // 如果当前执行的是第1天,那么下面返回的就是第二天奖牌发放之后剩余的数量.
            // 现在问题是: 我怎么用第二天结束之后的奖牌来求出第一天结束之后的奖牌数量.
            // 那我第一天用得到吗?
            // TODO: 这里出现死循环.
            num = num + 1;
            Long t = this.games(num);
            return ((7 / 6) * t + num);

        }

    }


    /**
     * 返回值表示第num天还没发奖牌时的总奖牌数, 所以说games(1) 表示的就是总的奖牌数.
     *
     * @param num
     * @return
     */
    public int games(int num) {

        // 如果不是第一天的结果,那么就应该为方法设置一个出口.
        // 这道题的出口就在于第num天还没有发奖牌开始之前就只剩下num个奖牌了,这一天发完就没了.
        // 这道题还是归结到找函数出口.
        // TODO: 只要找出该函数的一个出口就能计算出该结果.
        if (num == 0) {

            // 函数出口找不到. 

        }


        num++;
        return ((7 / 6) * this.games(num) + 1);

    }

    /**
     * 方法的入参只能是从1开始.
     *
     * @param num
     * @return
     */
    public double games(double num) {

        // 判断函数的出口.


        num++;
        double t = this.games(num);
        return (7 / 6) * t + 1;

    }

    /**
     * 国王分财产。某国王临终前给儿子们分财产。他把财产分为若干份，
     * 然后给第一个儿子一份，再加上剩余财产的1/10；
     * 给第二个儿子两份，再加上剩余财产的1/10；……；
     * 给第i个儿子i份，再加上剩余财产的1/10。每个儿子都窃窃自喜。
     * 以为得到了父王的偏爱，孰不知国王是“一碗水端平”的。请用程序回答，
     * 老国王共有几个儿子？财产共分成了多少份？
     *
     * 方法的入参是:
     *
     * @param i 第几个儿子.
     * @return 返回值表示第i个孩子分完之后应该剩余的总的财产数.
     */
    public Long king(Long i) {

        // 方法入参只有一个.
        // 财产一共分了.
        // 能假设当前i表示最后一个孩子吗?






        return 0L;
    }


}
