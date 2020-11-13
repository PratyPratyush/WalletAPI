package com.novaapi.wallet.services;

import com.novaapi.wallet.models.BankAccount;
import com.novaapi.wallet.models.CreditMoney;
import com.novaapi.wallet.models.MoneyTransfer;
import com.novaapi.wallet.models.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public interface WalletService {
    User createUser(User user);
    Map<String,String> addMoneyInWallet(CreditMoney addMoney);
    Map<String,String> transfer(MoneyTransfer transferObject);
    Map<String,Double> chargesAndCommission(double amount);
    Map<String,String> statusEnquiry(UUID transacionId);
    BankAccount viewPassbook(User user);
    boolean validateCredential(String email,String password);

}
