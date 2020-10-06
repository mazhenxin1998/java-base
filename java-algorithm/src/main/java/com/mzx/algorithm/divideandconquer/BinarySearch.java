package com.mzx.algorithm.divideandconquer;

/**
 * 二分搜索技术有一个前提要去是: 只能在已经排好序的元素中进行搜索.
 * <p>
 * 本次查找以Integer为例来进行.
 *
 * @author ZhenXinMa
 * @date 2020/8/31 12:32
 */
public class BinarySearch {


    /**
     * 二分搜索.
     *
     * @param arg    已排好序的数组.
     * @param target 要查找的元素.
     * @return 返回该元素在数组arg中的下标，该下标是从0开始.
     */
    public static Integer search(int[] arg, int target) {

        /*
         * --------------------------------------------------------
         *  书中给的算法有三个参数,其中第三个参数是数组的长度.
         * 现在需要在 arg[0]---arg[arg.length - 1] 之间进行搜索.
         * 二分搜索需要在每次执行循环的时候计算出当前中间的值 然后进行比较,
         * 而每次循环时中间的值都需要left和right来进行计算,因为这两个值在上一
         * 次循环的时候已经被更改了.
         *  ArrayList底层其实就是[]数组. 所以说这里的arg参数可以换成Java中
         * 的类.
         * --------------------------------------------------------
         * */
        int left = 0;
        int right = arg.length - 1;
        while (left <= right) {

            // 每次执行循环的时候应该先计算中间值.
            int middle = (left + right) / 2;
            if (target == arg[middle]) {

                // 进行第一次二分查找的时候就找到已经要查找的目标了.
                return middle;
            } else if (target > arg[middle]) {

                // 要查找的目标在上一次二分查找的的中间值的右侧.
                left = middle + 1;
            } else if (target < arg[middle]) {

                // 要查找的目标在上一次二分查找的中间值的左侧,说明该次查找的值要比上次的小.
                right = middle - 1;
            }

        }

        // 如果没有找到那么就返回-1；
        return -1;
    }

    public static void main(String[] args) {

        int[] a = new int[]{1,2,3,4,5,6,7,8,9,10};
        Integer search = BinarySearch.search(a, 5);
        System.out.println(search);

    }

}
