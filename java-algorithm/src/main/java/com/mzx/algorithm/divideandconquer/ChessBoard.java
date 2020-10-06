package com.mzx.algorithm.divideandconquer;

/**
 * @author ZhenXinMa
 * @date 2020/8/31 14:24
 */
public class ChessBoard {


    /**
     * 算法: 经典算法问题: 棋盘覆盖问题. 没搞出来.
     * <p>
     * 在一个2的k次方*2的k次方的棋盘上，愘有一个方格与其他的不同，则称该方格为特殊方格，然后用a,b,c,d四种L型骨牌对其剩余进行覆盖.
     * 描述不出来.  - -~
     *
     *
     * @param tx   棋盘左上角的行号.
     * @param ty   棋盘左上角的列号.
     * @param dx   特殊方格的行号.
     * @param dy   特殊方格的列号.
     * @param size 棋盘大小: 该值符合大小为2的k次幂
     */
    public static void ChessBoard(int tx, int ty, int dx, int dy, int size) {

        /*每四个*表示一个方框.
         * --------------------------------------------------------
         * 1、          2、                   3、           4、
         *  ********              ********         ****                 ****
         *  ********              ********         ****                 ****
         *  ********              ********         ****                 ****
         *  ********              ********         ****                 ****
         *  ****                      ****         ********         ********
         *  ****                      ****         ********         ********
         *  ****                      ****         ********         ********
         *  ****                      ****         ********         ********
         *
         * --------------------------------------------------------
         * */
        // 先排除k=0的情况.
        if (size == 1) {

            // 空方法可以直接退出.
            return;
        }

        // 默认使用第三种骨牌,如果在分块中有特殊方格,然后看情况而定.
        // L型骨牌.
        int t = 3;
        // 分割. size / 2 表示x和y的一半.
        int s = size / 2;
        // 解决左上角问题.
        if (dx < tx + s && dy < ty + s) {

            // 表示当前特殊方格在当前分割的位置上，当前分割的位置位于左上角.
            // 要注意这里的判断条件是<不是<=，也就是说, 当分割成最小元素快的时候是四个块为一个元素. 这个时候就不能在分割了.
            // 递归调用  由于这里递归调用的时候只是将棋盘大小换成了原来的1/4 并且由于这里是左上角所欲说前面参数不用改变.
            ChessBoard(tx, ty, dx, dy, s);
        } else {

            // 位于左上角进行使用骨牌来填充棋盘.
            // 方法: 特殊方格在最小元素中与左上角的关系.
            if (tx == dx && dy > ty) {

                System.out.println("使用第是三种骨牌.");
            } else if (dx > tx && dy > ty) {

                System.out.println("使用第一种骨牌.");
            } else if (dx == tx && dy == ty) {

                System.out.println("使用第四种骨牌.");
            } else {

                System.out.println("使用第二种骨牌.");
            }

            // 这里是不会完的,所以应该再次调用递归函数.
            ChessBoard(tx, ty, tx + s - 1, ty + s - 1, s);
        }

        // 解决右上角问题.
        if (dx < tx + s && dy > ty + s) {

            ChessBoard(tx, ty + s, dx, dy, s);
        } else {

            // 对其进行处理. 最中会化成最小元素进行处理.
            if (tx == dx && dy > ty) {

                System.out.println("使用第是三种骨牌.");
            } else if (dx > tx && dy > ty) {

                System.out.println("使用第一种骨牌.");
            } else if (dx == tx && dy == ty) {

                System.out.println("使用第四种骨牌.");
            } else {

                System.out.println("使用第二种骨牌.");
            }

            ChessBoard(tx, ty + s, tx + s - 1, ty + s, s);
        }

        if (dx >= tx + s && dy < ty + s) {

            ChessBoard(tx + s, ty, dx, dy, s);
        } else {

            if (tx == dx && dy > ty) {

                System.out.println("使用第是三种骨牌.");
            } else if (dx > tx && dy > ty) {

                System.out.println("使用第一种骨牌.");
            } else if (dx == tx && dy == ty) {

                System.out.println("使用第四种骨牌.");
            } else {

                System.out.println("使用第二种骨牌.");
            }

            ChessBoard(tx + s, ty, tx + s, ty + s - 1, s);
        }

        // 解决右下角.
        if (dx >= tx + s && dy >= ty + s) {

            // dx = dy = 5, tx = ty = 1 s = 4 ,
            // 5 5 5 5
            ChessBoard(tx + s, ty + s, dx, dy, s);
        } else {

            if (tx == dx && dy > ty) {

                System.out.println("使用第是三种骨牌.");
            } else if (dx > tx && dy > ty) {

                System.out.println("使用第一种骨牌.");
            } else if (dx == tx && dy == ty) {

                System.out.println("使用第四种骨牌.");
            } else {

                System.out.println("使用第二种骨牌.");
            }

            ChessBoard(tx + s, ty + s, tx + s, ty + s, s);
        }

    }

    public static void main(String[] args) {

        ChessBoard.ChessBoard(1, 1, 1, 1, 8);

    }


}
