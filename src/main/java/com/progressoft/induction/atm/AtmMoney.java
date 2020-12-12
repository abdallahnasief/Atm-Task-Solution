package com.progressoft.induction.atm;

import java.math.BigDecimal;

public class AtmMoney {
    private int countOfMoney;
    private final BigDecimal banknote;

    public AtmMoney(int countOfMoney, BigDecimal banknote) {
        this.countOfMoney = countOfMoney;
        this.banknote = banknote;
    }

    public int getCountOfMoney() {
        return countOfMoney;
    }


    public BigDecimal getBanknote() {
        return banknote;
    }


    public void decreaseTheCount() {
        this.countOfMoney--;
    }

    public Double getSumOfMoney() {
        return banknote.doubleValue() * countOfMoney;
    }
}
