package com.atm.beans.command;

import com.atm.beans.CashMachine;
import com.atm.beans.exception.InterruptOperationException;
import com.atm.beans.ConsoleHelper;
import com.atm.beans.CurrencyManipulator;
import com.atm.beans.CurrencyManipulatorFactory;
import com.atm.beans.exception.NotEnoughMoneyException;

import java.util.ResourceBundle;

class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en"); //14

    @Override
    public void execute() throws InterruptOperationException {

        ConsoleHelper.writeMessage(res.getString("before"));

        String code = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);

        while (true){
            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            String s = ConsoleHelper.readString();
            int summ;

            try {
                summ = Integer.parseInt(s);
            }catch (NumberFormatException e){
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }

            if(!manipulator.isAmountAvailable(summ)){
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }

            try {
                manipulator.withdrawAmount(summ);
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
                continue;
            }
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), summ, code));

            break;

        }
    }
}
