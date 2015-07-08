package com.atm.beans.command;

import com.atm.beans.CashMachine;
import com.atm.beans.exception.InterruptOperationException;
import com.atm.beans.ConsoleHelper;

import java.util.ResourceBundle;


class LoginCommand implements Command{

    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en"); //14


    @Override
    public void execute() throws InterruptOperationException {

        ConsoleHelper.writeMessage(res.getString("before"));

        while (true){
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            String inputCardNumber = ConsoleHelper.readString();
            String inputPin = ConsoleHelper.readString();

            if (validCreditCards.containsKey(inputCardNumber)){
                if (validCreditCards.getString(inputCardNumber).equals(inputPin)){
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), inputCardNumber));
                }else{
                    ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                    continue;
                }
            } else{
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), inputCardNumber));
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                continue;
            }

            break;

        }

    }

}
