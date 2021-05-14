package com.springcloud.test.fil;

import java.util.List;

import com.google.common.collect.Lists;
import com.springcloud.test.util.DecimalUtil;

/**
 * @ClassName FilCoinCal
 * @Description FilCoinCal
 * @Author gaoyao
 * @Date 2021/5/14 8:34 AM
 * @Version 1.0
 */
public class FilCoinCal {

    public static void main(String[] args) {
        double T_PER_COIN = 0.0665;
        double filBalance = 202;
        List<SingleCal> singleCalList = Lists.newArrayList();
        for (int i=0; i<365; i++) {
            T_PER_COIN = DecimalUtil.mul(T_PER_COIN, 1.0-0.01);
            setAllTPerCoin(singleCalList, T_PER_COIN);
            //获取收益
            filBalance = DecimalUtil.add(filBalance, getTodayFils(singleCalList));

            if (allCalIsFull(singleCalList)) {
                //增加机器
                if (SingleCal.canBuyMachine(filBalance)) {
                    SingleCal singleCal = new SingleCal();
                    filBalance -= singleCal.initAndAddT2();
                    singleCalList.add(singleCal);
                }

            } else {
                //增加算力
                for (SingleCal singleCal : singleCalList) {
                    if (singleCal.canAddT2(filBalance)) {
                        filBalance -= singleCal.addT2();
                    }
                }
            }

            System.out.println("fil余额：" + filBalance + ", 总算力：" + getTotalT(singleCalList) + ", 质押fil：" + getCostFils(singleCalList) + ", 每天产出：" + T_PER_COIN);
        }



    }

    private static void setAllTPerCoin(List<SingleCal> singleCalList, double t_per_coin) {
        for (SingleCal singleCal : singleCalList) {
            singleCal.setTPerCoin(t_per_coin);
        }
    }

    public static double getCostFils(List<SingleCal> singleCalList) {
        double fils = 0;
        for (SingleCal singleCal : singleCalList) {
            fils += singleCal.getDepistFils();
        }
        return fils;
    }

    public static int getTotalT(List<SingleCal> singleCalList) {
        int t = 0;
        for (SingleCal singleCal : singleCalList) {
            t += singleCal.getNowT();
        }
        return t;
    }

    public static double getTodayFils(List<SingleCal> singleCalList) {
        double fils = 0d;
        for (SingleCal singleCal : singleCalList) {
            fils = DecimalUtil.add(fils, singleCal.getFilPerDay());
        }
        return fils;
    }

    public static boolean allCalIsFull(List<SingleCal> singleCalList){
        for (SingleCal singleCal : singleCalList) {
            if (!singleCal.isFull()) {
                return false;
            }
        }
        return true;
    }


}
