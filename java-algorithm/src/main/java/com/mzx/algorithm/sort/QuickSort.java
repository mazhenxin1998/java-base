package com.mzx.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-10-04 14:19 周日.
 */
public class QuickSort {


    public static void quickSort(int[] array, int low, int high) {

        int i;
        int j;
        // tem就是基准位.
        int tem;
        int t;
        if (low > high) {

            return;

        }

        i = low;
        j = high;
        tem = array[i];
        // 档row < high的时候循环.
        // 快速排序: 从基准位两侧分别找出大于基准位的和小于基准位的进行交换.
        while (i < j) {

            // 右边往左依次递减.
            while (tem <= array[j] && i < j) {

                j--;

            }

            // 只要上面的while循环能退出，那么就说明基准位的右边有一个要比基准位的小.
            // 左边往右依次递增.
            while (tem >= array[i] && i < j) {

                i++;

            }

            if (i < j) {

                // 条件满足, 开始交换.
                t = array[i];
                array[i] = array[j];
                array[j] = t;

            }

        }

        // 这两个步骤到底意味着什么?
        // 第i个位置一定需要是空的? 有必要进行交换?
        // 进行一次之后，如果要对第一个基准的左边进行挑选基准，那么应该使用第一个基准左侧第一个数作为新的基准来进行排序.
        // 这里的array[i]是交换之后的array[i].
        // 这里重新计算low的值.
        // low的值第一次是0.
        // tem = array[i]
        array[low] = array[i];
        // tem是基准位.
        array[i] = tem;
        // 递归调用左半边数组.
        quickSort(array, low, j - 1);
        // 递归调用右半边数组.
        quickSort(array, low + 1, high);

    }


    public static void sort(int[] array, int left, int right) {

        // 记录下下标.
        int l = left;
        int r = right;
        // 找到中轴值.
        int p = (left + right) / 2;
        int pivot = array[p];
        // 作为交换时的中间变量.
        int temp = 0;
        while (l < r) {

            // 从基准位开始找到一个比基准位表示大的值并且放到右边.
            while (array[l] < pivot) {

                l++;

            }

            // 从基准位开始找到一个比基准为表示小的值并且放入到左边.
            while (array[r] > pivot) {

                r--;

            }

            // 说明pivot左右两边的值已经是排好序的了.
            if (l >= r) {

                // 如果 l > r 那么就直接退出.
                break;

            }

            // 直接交换.
            temp = array[l];
            array[l] = array[r];
            array[r] = temp;

        }

        if (l == r) {

            l++;
            r--;

        }

        // 向左递归.
        if (left < r) {

            // 上面的循环结束, 那么r已经变化了.
            sort(array, left, p-1);

        }

        // 向右递归.
        if (right > l) {

            sort(array, p+1,right);

        }

    }

    public static void main(String[] args) {

        int[] array = new int[]{1,3,2};
        sort(array, 0, array.length - 1);
        for (int i : array) {

            System.out.println(i);

        }


    }


}
