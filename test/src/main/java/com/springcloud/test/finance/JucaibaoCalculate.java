package com.springcloud.test.finance;

import com.springcloud.test.util.DecimalUtil;

/**
 * @ClassName 计算寿险与聚财宝 价值
 * @Description ShouXianCalculate
 * @Author gaoyao
 * @Date 2021/4/15 10:52 AM
 * @Version 1.0
 */
public class JucaibaoCalculate {

    public static void main(String[] args) {
        //年利率
        double yearRate = 0.150906;
        //月利率
        double monthRate = DecimalUtil.div(yearRate, 12);
        //每年投入
        double initMoney = 3000000;

        double nowTotalMoney = initMoney;
        int months = 12;


        for (int i=0; i< months; i++) {
            nowTotalMoney = DecimalUtil.add(nowTotalMoney, DecimalUtil.mul(nowTotalMoney, monthRate));
        }

        System.out.println("投资"+ months +"个月后总金额："+nowTotalMoney);

    }

}
