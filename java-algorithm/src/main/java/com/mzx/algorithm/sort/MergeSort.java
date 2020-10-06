package com.mzx.algorithm.sort;

import java.util.Arrays;

/**
 * 归并排序.
 *
 * @author ZhenXinMa.
 * @slogan 脚踏实地向前看.
 * @create 2020-10-04 15:19 周日.
 */
public class MergeSort {


    public static void main(String[] args) {

        int[] array = {5,8,4,2,3,2,1,2,5,6,9,4,2,3,5,8};
        int[] temp = new int[array.length];
        mergeSort(array, 0, array.length - 1, temp);
        System.out.println(Arrays.toString(temp));


    }

    /**
     * 合并.
     *
     * @param array 要排序的数组.
     * @param left  排序数组最左边的下标》
     * @param right 排序数组最右边的下标.
     * @param temp  临时数组.
     */
    public static void mergeSort(int[] array, int left, int right, int[] temp) {

        if (left < right) {

            int mid = (left + right) / 2;
            // 左递归.
            mergeSort(array, left, mid, temp);
            mergeSort(array, mid + 1, right, temp);
            // 合并.
            merge(array, left, mid, right, temp);

        }

    }

    /**
     * 合并.
     *
     * @param array 要排序的数组.
     * @param left  每次要合并的左下标.
     * @param mid   中间下标.
     * @param right 每次要合并的右下标.
     * @param temp  中间数组.
     */
    public static void merge(int[] array, int left, int mid, int right, int[] temp) {

        int i = left;
        int j = mid + 1;
        // 表示temp数组下标的位置.
        int t = 0;
        // 只要有一边数组完毕那么合并基本上就完毕了.
        while (i <= mid && j <= right) {

            if (array[i] <= array[j]) {

                // 左边的比右边的小.
                temp[t] = array[i];
                t++;
                i++;

            } else {

                // 右边的要比左边的小.
                temp[t] = array[j];
                t++;
                j++;

            }

        }

        // 如果代码能进行到这里，那么只需要将最后的元素赋值到temp中.
        // 左边还有剩余的.
        while (i <= mid) {

            temp[t] = array[i];
            i++;
            t++;

        }

        // 右边还有剩余.
        while (j <= right) {

            // 这是有问题?
            temp[t] = array[j];
            j++;
            t++;

        }

        // 最后需要将temp中的数组拷贝到原数组中.
        // 但是这里不是每一次都拷贝所有.
        // 就以第一次来说: 其拷贝的是0和1.
        // 重置临时数组的下标.
        t = 0;
        int temLeft = left;
        while (temLeft <= right) {

            // 拷贝.
            array[temLeft] = temp[t];
            temLeft++;
            t++;

        }

    }

}




