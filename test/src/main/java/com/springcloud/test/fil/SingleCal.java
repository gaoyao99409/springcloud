package com.springcloud.test.fil;

import com.springcloud.test.util.DecimalUtil;

/**
 * @ClassName SingleCal
 * @Description SingleCal
 * @Author gaoyao
 * @Date 2021/5/14 9:15 AM
 * @Version 1.0
 */
public class SingleCal {

    //最大算力
    private final static int T_MAX = 10;
    //每个算力花费fil数
    private final static double T_PER_COST_FIL = 10d;
    //初始购买机器需要fil数
    private final static double T_INIT_COST_FIL = 33d;
    //每T算力产出
    private double T_PER_COIN = 0.0665;
    //平台扣除产出百分比
    private final static double TAKE_PERCENT = 0.2;
    //初始算力0
    private int nowT = 0;
    //买算力花费
    private double costFil = 0;
    //第n天
    private int days = 1;
    //产出减少率
    private double subtract = 0.05;

    /**
     * 增加2T算力
     * @return  需要质押及GAS等FIL数
     */
    public double addT2(){
        int addT = 2;
        if (nowT >= T_MAX) {
            return 0d;
        }
        this.nowT += addT;
        this.costFil += DecimalUtil.mul(T_PER_COST_FIL, addT);

        return DecimalUtil.mul(T_PER_COST_FIL, addT);
    }

    /**
     *
     * @return
     */
    public double initAndAddT2(){
        addT2();
        return DecimalUtil.add(T_INIT_COST_FIL, DecimalUtil.mul(T_PER_COST_FIL, 2));
    }

    /**
     * 每天产fil数
     * @return
     */
    public double getFilPerDay(){
        return DecimalUtil.mul((1-TAKE_PERCENT), DecimalUtil.mul(T_PER_COIN, nowT));
    }

    public double getCostFil() {
        return this.costFil;
    }

    public double getFullCalCostFil(){
        return DecimalUtil.add(T_INIT_COST_FIL, DecimalUtil.mul(T_MAX, T_PER_COST_FIL));
    }

    public boolean isFull(){
        return nowT >= T_MAX;
    }

    public static boolean canBuyMachine(double balance){
        return balance >= DecimalUtil.add(T_INIT_COST_FIL, DecimalUtil.mul(2, T_PER_COST_FIL));
    }

    public boolean canAddT2(double balance) {
        return !isFull() && (balance >= DecimalUtil.mul(T_PER_COST_FIL, 2));
    }

    public int getNowT() {
        return nowT;
    }

    public double getDepistFils(){
        return DecimalUtil.mul(0.8, this.costFil);
    }

    public void setTPerCoin(double t) {
        T_PER_COIN = t;
    }
}
