package com.mzx.algorithm.recall;

/**
 * 八皇后问题.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-10-04 13:22 周日.
 */
public class Queen {

    static int n = 8;
    static int[] c = new int[n + 1];
    private int sum;

    public Queen(int n) {

        n = n;

    }

    /**
     * row即表示当前棋盘的第几行也表示第几个皇后.
     *
     * @param row
     * @return
     */
    public boolean is_ok(int row) {

        for (int i = 1; i < row; i++) {

            if (c[row] == c[i] || ((row - i == c[row] - c[i]) && ((row - i == c[i] - c[row])))) {

                // 主要是满足条件的直接返回false.
                return false;

            }

        }

        return true;

    }

    public void add(int index) {

        // 递归出口.
        if (index == 9) {

            this.sum++;
            return;

        }

        for (int i = 0; i < c.length; i++) {

            if (index != 9) {

                // 表示第index个皇后假设处于c[index]列上.
                c[index] = 1;
                c[index] = i;
                if (this.is_ok(index)) {

                    this.add(++index);

                }

            } else {

                return;

            }

        }

    }

    public static void main(String[] args) {

        // 八皇后问题.
        Queen queen = new Queen(8);
        queen.add(1);
        System.out.println(queen.sum);


    }

}
