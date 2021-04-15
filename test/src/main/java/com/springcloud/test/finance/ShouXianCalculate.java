package com.springcloud.test.finance;

import com.springcloud.test.util.DecimalUtil;

/**
 * @ClassName ShouXianCalculate
 * @Description ShouXianCalculate
 * @Author gaoyao
 * @Date 2021/4/15 10:52 AM
 * @Version 1.0
 */
public class ShouXianCalculate {

    public static void main(String[] args) {
        double expectTotalMoney = 3000000d;
        double yearRate = 0.15;
        double monthRate = DecimalUtil.div(yearRate, 12);
        double yearInMoney = 50000.0;
        double monthInMoney = DecimalUtil.div(yearInMoney, 12).doubleValue();
        int moneyInYears = 20;

        int nowMonthCount = 0;
        double nowTotalMoney = 0d;

        double rateMoney =0;
        while (nowTotalMoney < expectTotalMoney) {
            ++ nowMonthCount;

            rateMoney = DecimalUtil.mul(nowTotalMoney, monthRate);
            nowTotalMoney = DecimalUtil.add(nowTotalMoney, rateMoney);

            if (nowMonthCount <= moneyInYears * 12) {
                nowTotalMoney = DecimalUtil.add(nowTotalMoney, monthInMoney);
            }

        }
        System.out.println("年利率：" + yearRate*100 +"%");
        System.out.println("达到"+expectTotalMoney +"月数：" + nowMonthCount);
        System.out.println("达到"+expectTotalMoney +"年数：" + nowMonthCount/12);
        System.out.println("达到"+expectTotalMoney +"月收益：" + rateMoney);
    }
}
