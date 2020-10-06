package com.mzx.algorithm.divideandconquer;

/**
 * 快速排序.
 *
 * @author ZhenXinMa
 * @date 2020/8/31 15:41
 */
public class QuickSort {

    public static void quickSort(int[] arr,int low,int high){
        int i,j,temp,t;
        if(low>high){
            return;
        }
        i=low;
        j=high;
        //temp就是基准位
        temp = arr[low];

        while (i<j) {
            //先看右边，依次往左递减
            while (temp<=arr[j]&&i<j) {
                j--;
            }
            //再看左边，依次往右递增
            while (temp>=arr[i]&&i<j) {
                i++;
            }
            //如果满足条件则交换
            if (i<j) {
                t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }

        }
        //最后将基准为与i和j相等位置的数字交换
        arr[low] = arr[i];
        arr[i] = temp;
        //递归调用左半数组
        quickSort(arr, low, j-1);
        //递归调用右半数组
        quickSort(arr, j+1, high);
    }




    /**
     * 快速排序 一趟快排.
     * <p>
     * 按照书上的写法,参数的意思为:
     *
     * @param source 待排序的数组.
     * @param high   右侧指针.
     */
    public void quickSort1(int[] source, int low, int high) {

        // 将第一个元素的位置当做支点.
        source[0] = source[low];
        // 默认是子数组未排好序的左侧的第一个元素.
        // key中保存永远是马上被替换掉的值,
        int key = source[low];
        while (low < high) {

            // 第一此排序是从最右边开始的.
            while (low < high && source[high] >= key) {

                // 如果右侧的一直大于支点,那么就循环从右往左跟支点进行比较.
                high--;
            }

            // 如果代码执行到了这里,说明source[high]比支点的值要小.
            // 这个时候,将比支点小的值,放入到low位置上.
            // source[low]虽然被替换调了,
            source[low] = source[high];
        }


    }



}
