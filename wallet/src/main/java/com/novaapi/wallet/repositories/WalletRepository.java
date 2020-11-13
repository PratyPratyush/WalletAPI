package com.novaapi.wallet.repositories;

import com.novaapi.wallet.exception.InsufficientAmount;
import com.novaapi.wallet.exception.InvalidCredentials;
import com.novaapi.wallet.models.BankAccount;
import com.novaapi.wallet.models.CreditMoney;
import com.novaapi.wallet.models.MoneyTransfer;
import com.novaapi.wallet.models.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class WalletRepository {
    Map<String, User> userDetail = new ConcurrentHashMap<>();
    Map<String, BankAccount> userAccount  = new ConcurrentHashMap<>();
    Map<UUID, String> trasactionStatus  = new ConcurrentHashMap<>();
    public static final double charges = 0.02;
    public static final double commision = 0.05;


    public boolean emailAvailability(String email){
        if(userDetail.containsKey(email)){
            return true;
        }
        return false;
    }
    public void addUser(User user){
        userDetail.put(user.getEmail(),user);
        BankAccount account = new BankAccount();
        account.setAccountName(user.getName());
        account.setAccountId(user.getEmail());
        account.setAmount(0.0);
        userAccount.put(user.getEmail(),account);
    }
    public boolean validateUserCredential(String email,String password){

        User user = userDetail.get(email);
        if(user==null)
            throw new InvalidCredentials("Invalid email/password.");
        if(!user.getEmail().equals(email)
                || !user.getPassword().equals(password)){
            throw new InvalidCredentials("Invalid email/password.");
        }
        return true;
    }
    public Map<String,String> addMoneyInWallet(CreditMoney addMoney){
        BankAccount bankAccount = userAccount.get(addMoney.getEmail());
        UUID transactionId = UUID.randomUUID();
        Map<String,String> response = new HashMap<>();
        double currentAmount = addMoney.getAmount();
        double serviceCharge = ((currentAmount*charges) + (currentAmount*commision));
        boolean status = bankAccount.credit(currentAmount-serviceCharge,transactionId);
        response.put("transaction",transactionId+"");
        if(!status){
            trasactionStatus.put(transactionId,"Failed");
            response.put("status","failed");
        }else{
            trasactionStatus.put(transactionId,"Success");
            response.put("status","success");
        }
        userAccount.put(addMoney.getEmail(),bankAccount);
        return response;
    }
    public Map<String,Double> chargesAndCommission(double amount){
        Map<String,Double> response = new HashMap<>();
        double currentCharges = amount*charges;
        double currentCommission = amount*commision;
        response.put("Charges",currentCharges);
        response.put("Commission",currentCommission);
        return response;
    }

    public Map<String,String> transfer(MoneyTransfer transferObject){

        Map<String,String> response = new ConcurrentHashMap<>();
        BankAccount senderAccount = userAccount.get(transferObject.getSenderAccountId());
        BankAccount receiverAccount = userAccount.get(transferObject.getReceiverAccountId());
        UUID transactionId = null;
        double amount = transferObject.getAmount();
        double serviceCharge = ((amount*charges) + (amount*commision));
        if(senderAccount.getAmount()<(amount+serviceCharge)){
            throw new InsufficientAmount("Insufficient amount.Transaction also " +
                    "include charge(0.2%) and commission(0.05%) ");
        }
        transactionId = UUID.randomUUID();
        boolean debitStatus = senderAccount.debit(amount,transactionId);
        boolean creditStatus = receiverAccount.credit((amount-serviceCharge),transactionId);
        response.put("transactionId",transactionId+"");
        if(debitStatus && creditStatus){
            response.put("status","success");
            trasactionStatus.put(transactionId,"success");
        }else{
            response.put("status","failed");
            trasactionStatus.put(transactionId,"failed");
        }
        userAccount.put(senderAccount.getAccountId(),senderAccount);
        userAccount.put(receiverAccount.getAccountId(),receiverAccount);
        return response;
    }

    public Map<String,String> statusEnquery(UUID transactionId){
        String status = trasactionStatus.get(transactionId);
        Map<String,String> response = new HashMap<>();
        if(status==null){
            response.put("transactionId ",transactionId+"");
            response.put("msg","Invalid transactionId");
            return response;
        }
        response.put("transactionId ",transactionId+"");
        response.put("Status",status);
        return response;
    }
    public BankAccount viewPassbook(User user){
        BankAccount account = userAccount.get(user.getEmail());
        return account;
    }

}
