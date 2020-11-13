package com.novaapi.wallet.models;

public class CreditMoney {
    private String email;
    private String password;
    private double amount;
    public CreditMoney(){

    }
    public CreditMoney(String email, String password, double amount) {
        this.email = email;
        this.password = password;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
