package com.novaapi.wallet.models;

public class MoneyTransfer {
    private String senderAccountId;
    private String senderPassword;
    private String receiverAccountId;
    private double amount;
    MoneyTransfer(){

    }
    public MoneyTransfer(String senderAccountId, String senderPassword, String receiverAccountId, double amount) {
        this.senderAccountId = senderAccountId;
        this.senderPassword = senderPassword;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
    }

    public String getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(String senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public String getSenderPassword() {
        return senderPassword;
    }

    public void setSenderPassword(String senderPassword) {
        this.senderPassword = senderPassword;
    }

    public String getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(String receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
