package com.mzx.algorithm.util;

import com.sun.istack.internal.NotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @author ZhenXinMa.
 * @slogan 浮生若梦, 若梦非梦, 浮生何梦? 如梦之梦.
 * @date 2020/9/13 15:56.
 */
public class NumberUtils {

    public static double divider(@NotNull int fz, @NotNull int fm) {

        DecimalFormat df = new DecimalFormat("0.00");
        // 现在返回的这个值有一个前提条件: 那就是必须是整数.
        // 向高位对齐.
        return Double.parseDouble(df.format((double) fz / (fm)));

    }

    /**
     * 提供相对精确的除法问题.
     * @param fz
     * @param fm
     * @return
     */
    public static double divider(double fz , double fm){

        // 现在乘法是偶遇问题的。

        Double result = fz / fm;
        BigDecimal k = new BigDecimal(result);
        Double rr = k.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();


        return rr;

    }



    public static void main(String[] args) {

        // 这个是9.99 最后是算不出来的.
        int d = (int) (divider(10, 9) * 9);
        System.out.println(d);
        // 现在由于精度问题还是计算不出来.
        int dd = (10 / 9) * 9 + 8;
        System.out.println(dd);

    }

}
