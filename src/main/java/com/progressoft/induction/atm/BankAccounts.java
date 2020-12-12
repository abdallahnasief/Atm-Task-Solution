package com.progressoft.induction.atm;

import java.math.BigDecimal;

public class BankAccounts {

    private final String accountNumber;
    private BigDecimal amount;

    public BankAccounts(String accountNumber, BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void updateAmount(BigDecimal amount) {
        this.amount = this.amount.subtract(amount);
    }

}
