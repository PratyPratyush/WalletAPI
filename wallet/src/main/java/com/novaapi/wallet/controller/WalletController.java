package com.novaapi.wallet.controller;

import com.novaapi.wallet.models.BankAccount;
import com.novaapi.wallet.models.CreditMoney;
import com.novaapi.wallet.models.MoneyTransfer;
import com.novaapi.wallet.models.User;
import com.novaapi.wallet.services.WalletService;
import com.novaapi.wallet.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class WalletController {

    @Autowired
    WalletService walletService;

    @Autowired
    Validator validator;


    @PostMapping("/Sign-up")
    public ResponseEntity<User> createUser(@RequestBody User user){
        if(validator.validateUserObject(user)){
            user = walletService.createUser(user);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        return ResponseEntity.badRequest().body(user);
    }

    @PostMapping("/addMoney")
    public ResponseEntity<?> addMoney(@RequestBody CreditMoney addMoney){
        Map<String,String> result = walletService.addMoneyInWallet(addMoney);
        if(result==null){
            ResponseEntity.badRequest().body(" Operation Failed");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody MoneyTransfer transferObject){
            Map<String,String> reponse = walletService.transfer(transferObject);
            if(reponse==null){
                return ResponseEntity.badRequest().body("Operation failed");
            }
            return ResponseEntity.ok(reponse);
    }
    @GetMapping("/serviceCharge/{amount}")
    public ResponseEntity<?> serviceCharge(@PathVariable(name="amount") Double amount){
            Map<String,Double> result = walletService.chargesAndCommission(amount);
            if(result==null){
                ResponseEntity.badRequest().body("Operation Failed");
            }
            return ResponseEntity.ok(result);
    }

    @PostMapping("/viewPassbook")
    public ResponseEntity<?> viewPassbook(@RequestBody User user){
        BankAccount account = new BankAccount();
        account = walletService.viewPassbook(user);
        if(account==null){
            return ResponseEntity.badRequest().body(account);
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<?> transactionStatus(@PathVariable(name="id") UUID id){
        Map<String,String> result = walletService.statusEnquiry(id);
        return ResponseEntity.badRequest().body(result);
    }


}
