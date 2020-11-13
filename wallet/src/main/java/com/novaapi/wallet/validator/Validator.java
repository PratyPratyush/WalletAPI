package com.novaapi.wallet.validator;

import com.novaapi.wallet.exception.InvalidArgumentException;
import com.novaapi.wallet.models.CreditMoney;
import com.novaapi.wallet.models.MoneyTransfer;
import com.novaapi.wallet.models.User;
import org.springframework.stereotype.Component;

@Component
public class Validator {

    public boolean validateUserObject(User user){
        if(user.getPassword() ==null || user.getName() ==null
                || user.getEmail() ==null){
            throw new InvalidArgumentException("name/email/password/ not filled completely");
        }
        return true;
    }
    public boolean validateAddMoneyParameter(CreditMoney addMoney){
        double zero = 0.0;
        double amount  = addMoney.getAmount();
        if(addMoney.getEmail() ==null || addMoney.getPassword()==null){
            throw new InvalidArgumentException("email/password/amount not filled properly");
        }
        if(amount<=zero){
            throw new InvalidArgumentException("amount must be grater than zero");
        }
        return true;
    }

    public boolean validateTransferPrameter(MoneyTransfer transferObject){
        double zero = 0.0;
        if(transferObject.getSenderAccountId()==null
            || transferObject.getReceiverAccountId() == null
            || transferObject.getSenderPassword() == null){
            throw new InvalidArgumentException("senderAccountId/senderPassword/receiverAccountId" +
                    " not filled properly");
        }
        if(transferObject.getAmount()<=zero){
            throw new InvalidArgumentException("amount parameter should be grater than zero");
        }
        return true;
    }
    public boolean validatePassbookUser(User user){
        if(user.getEmail()==null || user.getPassword()==null){
            throw new InvalidArgumentException("email/password should be completely filled");
        }
        return true;
    }

}
