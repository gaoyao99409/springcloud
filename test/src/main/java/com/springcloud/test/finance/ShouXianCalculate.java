package com.springcloud.test.finance;

import com.springcloud.test.util.DecimalUtil;

/**
 * @ClassName 计算寿险与聚财宝 价值
 * @Description ShouXianCalculate
 * @Author gaoyao
 * @Date 2021/4/15 10:52 AM
 * @Version 1.0
 */
public class ShouXianCalculate {

    public static void main(String[] args) {
        //期望到达
        double expectTotalMoney = 3000000d;
        //年利率
        double yearRate = 0.15;
        //月利率
        double monthRate = DecimalUtil.div(yearRate, 12);
        //每年投入
        double yearInMoney = 50000.0;
        //每月投入
        double monthInMoney = DecimalUtil.div(yearInMoney, 12).doubleValue();
        //投入年限
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
