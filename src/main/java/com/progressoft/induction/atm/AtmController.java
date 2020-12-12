package com.progressoft.induction.atm;

import com.progressoft.induction.atm.exceptions.AccountNotFoundException;
import com.progressoft.induction.atm.exceptions.InsufficientFundsException;
import com.progressoft.induction.atm.exceptions.NotEnoughMoneyInATMException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AtmController implements ATM, BankingSystem {

    private final ArrayList<BankAccounts> bankAccountList;
    private BigDecimal atmBalance;
    private AtmMoney fifty;
    private AtmMoney twenty;
    private AtmMoney ten;
    private AtmMoney five;

    public AtmController() {
        this.bankAccountList = new ArrayList<>();
        initialAccounts();
    }

    private void initialAccounts() {
        this.bankAccountList.add(new BankAccounts("123456789", new BigDecimal("1000.0")));
        this.bankAccountList.add(new BankAccounts("111111111", new BigDecimal("1000.0")));
        this.bankAccountList.add(new BankAccounts("222222222", new BigDecimal("1000.0")));
        this.bankAccountList.add(new BankAccounts("333333333", new BigDecimal("1000.0")));
        this.bankAccountList.add(new BankAccounts("444444444", new BigDecimal("1000.0")));
        fifty = new AtmMoney(10, Banknote.FIFTY_JOD.getValue());
        twenty = new AtmMoney(20, Banknote.TWENTY_JOD.getValue());
        ten = new AtmMoney(100, Banknote.TEN_JOD.getValue());
        five = new AtmMoney(100, Banknote.FIVE_JOD.getValue());
        atmBalance = BigDecimal.valueOf(fifty.getSumOfMoney() + twenty.getSumOfMoney() + ten.getSumOfMoney() + five.getSumOfMoney());
    }

    @Override
    public List<Banknote> withdraw(String accountNumber, BigDecimal amount) {
        BankAccounts account = getAccount(accountNumber);
        List<Banknote> banknoteList;
        if (account == null) {
            throw new AccountNotFoundException();
        } else if (getAccountBalance(accountNumber).compareTo(amount) < 0) {
            throw new InsufficientFundsException();
        } else if (atmBalance.compareTo(amount) < 0) {
            throw new NotEnoughMoneyInATMException();
        } else {
            int fiftyCount = 0;
            int twentyCount = 0;
            int tenCount = 0;
            int fiveCount = 0;
            banknoteList = new ArrayList<>();
            atmBalance = atmBalance.subtract(amount);
            debitAccount(accountNumber, amount);
            System.out.println("An amount of " + amount + " JOD was withdrawn from the account number : " + accountNumber);
            while (amount.compareTo(five.getBanknote()) >= 0) {
                if (amount.compareTo(five.getBanknote()) >= 0 && five.getCountOfMoney() > 0) {
                    fiveCount++;
                    amount = amount.subtract(five.getBanknote());
                    banknoteList.add(Banknote.FIVE_JOD);
                    five.decreaseTheCount();
                }
                if (amount.compareTo(ten.getBanknote()) >= 0 && ten.getCountOfMoney() > 0) {
                    tenCount++;
                    amount = amount.subtract(ten.getBanknote());
                    banknoteList.add(Banknote.TEN_JOD);
                    ten.decreaseTheCount();
                }
                if (amount.compareTo(twenty.getBanknote()) >= 0 && twenty.getCountOfMoney() > 0) {
                    twentyCount++;
                    amount = amount.subtract(twenty.getBanknote());
                    banknoteList.add(Banknote.TWENTY_JOD);
                    twenty.decreaseTheCount();
                }
                if (amount.compareTo(fifty.getBanknote()) >= 0 && fifty.getCountOfMoney() > 0) {
                    fiftyCount++;
                    amount = amount.subtract(fifty.getBanknote());
                    banknoteList.add(Banknote.FIFTY_JOD);
                    fifty.decreaseTheCount();
                }
            }
            System.out.println("The ATM returned " + fiftyCount + " 50-JOD banknotes");
            System.out.println("The ATM returned " + twentyCount + " 20-JOD banknotes");
            System.out.println("The ATM returned " + tenCount + " 10-JOD banknotes");
            System.out.println("The ATM returned " + fiveCount + " 5-JOD banknotes");
            System.out.println("The remaining amount in account number " + accountNumber + " is : " + account.getAmount() + "-JOD\n");
        }
        return banknoteList;
    }

    @Override
    public BigDecimal getAccountBalance(String accountNumber) {
        BankAccounts resultAccount = getAccount(accountNumber);
        return resultAccount.getAmount();
    }

    @Override
    public void debitAccount(String accountNumber, BigDecimal amount) {
        BankAccounts resultAccount = getAccount(accountNumber);
        resultAccount.updateAmount(amount);
    }


    public BankAccounts getAccount(String accountNumber) {
        BankAccounts resultAccount = null;
        for (BankAccounts bankAccount : bankAccountList) {
            if (bankAccount.getAccountNumber().equals(accountNumber)) {
                resultAccount = bankAccount;
            }
        }
        return resultAccount;
    }


}
