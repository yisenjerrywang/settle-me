package com.example.jerrywang.settleme;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by JerryWang on 1/16/2015.
 */
public class Debt {
    private String name;
    private double money;

    public Debt(String startName) {
        name = startName;
        money = 0;
    }

    public Debt(String startName, double startMoney) {
        name = startName;
        money = startMoney;
    }

    public void setName(String newValue) {
        name = newValue;
    }

    public String getName() {
        return name;
    }

    public void setMoney(double newValue) {
        money = newValue;
    }

    public double getMoney() {
        return money;
    }

    @Override
    public String toString() {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return name + ": " + "$" + formatter.format(money);
    }

}
