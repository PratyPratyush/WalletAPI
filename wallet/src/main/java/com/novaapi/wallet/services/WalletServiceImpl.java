package com.novaapi.wallet.services;

import com.novaapi.wallet.exception.InvalidArgumentException;
import com.novaapi.wallet.exception.InvalidCredentials;
import com.novaapi.wallet.exception.UnknownException;
import com.novaapi.wallet.models.BankAccount;
import com.novaapi.wallet.models.CreditMoney;
import com.novaapi.wallet.models.MoneyTransfer;
import com.novaapi.wallet.models.User;
import com.novaapi.wallet.repositories.WalletRepository;
import com.novaapi.wallet.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    Validator validator;

    @Override
    public User createUser(User requestUser) {
        User user = new User();
        if(walletRepository.emailAvailability(requestUser.getEmail())){
            throw new InvalidArgumentException("Email ID already present");
        }
        user.setEmail(requestUser.getEmail());
        user.setName(requestUser.getName());
        user.setPassword(requestUser.getPassword());
        walletRepository.addUser(user);
        return user;
    }

    @Override
    public boolean validateCredential(String email, String password) {

        return walletRepository.validateUserCredential(email,password);
    }

    @Override
    public Map<String,String> addMoneyInWallet(CreditMoney addMoney) {

        if(validator.validateAddMoneyParameter(addMoney) &&
            validateCredential(addMoney.getEmail(),addMoney.getPassword())){

             return walletRepository.addMoneyInWallet(addMoney);
        }
        throw new UnknownException("Unknown Exception");
    }

    @Override
    public Map<String,String> transfer(MoneyTransfer transferObject) {
        if(validator.validateTransferPrameter(transferObject)
            && validateCredential(transferObject.getSenderAccountId(),
                transferObject.getSenderPassword())){
            if(!walletRepository.emailAvailability(transferObject.getReceiverAccountId())){
                throw new InvalidCredentials("Please enter valid sender accountID");
            }
            return walletRepository.transfer(transferObject);
        }
        throw new UnknownException("Unknown Exception");
    }

    @Override
    public Map<String, Double> chargesAndCommission(double amount) {
        double zero = 0.0;
        if(amount<=zero){
            throw new InvalidArgumentException("Amount should be garter than zero");
        }
        return walletRepository.chargesAndCommission(amount);
    }

    @Override
    public Map<String,String> statusEnquiry(UUID transacionId) {
        if(transacionId==null){
            throw new InvalidArgumentException("Invalid transaction.");
        }
        return walletRepository.statusEnquery(transacionId);
    }

    @Override
    public BankAccount viewPassbook(User user) {
        BankAccount account = null;
        if(validator.validatePassbookUser(user)
            && validateCredential(user.getEmail(),user.getPassword())){
            account = walletRepository.viewPassbook(user);
        }
        return account;
    }
}
