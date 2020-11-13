package com.novaapi.wallet.models;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BankAccount {
    private String accountId;
    private String accountName;
    private double amount;
    public Map<UUID,String> transaction = new ConcurrentHashMap<>();

    public BankAccount(){

    }
    public BankAccount(String accountId, String accountName, double amount) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.amount = amount;
    }
    public boolean debit(double currentAmount,UUID transactionId){
        synchronized (this){
            amount = amount-currentAmount;
            this.transaction.put(transactionId,"Rs : "+currentAmount+" has been debited.");
            return true;
        }
    }
    public boolean credit(double currentAmount,UUID transactionId){
        synchronized (this){
            amount = amount+currentAmount;
            this.transaction.put(transactionId,"Rs : "+currentAmount+" has been credit.");
            return true;
        }
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
