package com.springcloud.test.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalUtil {
    private static DecimalFormat df = new DecimalFormat("0.00");
    private static DecimalFormat df2 = null;

    public static String format(Double amount) {
        if (amount == null) {
            return "0.00";
        }
        return df.format(amount);
    }

    public static String format(Double amount, String format) {
        df2 = new DecimalFormat(format);
        if (amount == null) {
            return format;
        }
        return df2.format(amount);
    }

    /**
     * 米转换成千米
     *
     * @param m
     * @return
     */
    public static double mToKm(Integer m) {
        double dis = 0;
        dis = Math.round(m / 100d) / 10d;
        return dis;
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static Double mul(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.multiply(b2).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的除法运算。 保留两位小数，四舍五入
     *
     * @param value1 被除数
     * @param value2 除数
     * @return 两个参数相除
     */
    public static Double div(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的加法运算。
     *
     * @param value1 被加数
     * @param value2 加数
     * @return 两个参数的和
     */
    public static Double add(Number value1, Number value2) {
        if (value1 == null) {
            value1 = 0;
        }
        if (value2 == null) {
            value2 = 0;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.add(b2).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的加法运算。
     *
     * @param values 多个需要相加的数
     * @return 两个参数的和
     */
    public static Double addAll(Number... values) {
        if (values == null) {
            return 0d;
        }
        if (values.length == 0) {
            return 0d;
        }

        BigDecimal totol = BigDecimal.ZERO;
        for (Number number : values) {
            if (number == null) {
                continue;
            }
            totol = BigDecimal.valueOf(add(totol, number));
        }

        return totol.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 减法
     *
     * @param value1
     * @param value2
     * @return
     */
    public static Double subtract(Number value1, Number value2) {
        if (value1 == null) {
            value1 = 0;
        }
        if (value2 == null) {
            value2 = 0;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.subtract(b2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
