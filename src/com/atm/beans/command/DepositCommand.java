package com.atm.beans.command;

import com.atm.beans.CashMachine;
import com.atm.beans.exception.InterruptOperationException;
import com.atm.beans.ConsoleHelper;
import com.atm.beans.CurrencyManipulator;
import com.atm.beans.CurrencyManipulatorFactory;

import java.util.ResourceBundle;

/**
 * Created by Alexander on 12.06.2015.
 */
class DepositCommand implements Command {

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en"); //13

    CurrencyManipulator manipulator;

    @Override
    public void execute() throws InterruptOperationException {

        ConsoleHelper.writeMessage(res.getString("before"));
        String currencyCode = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        String[] moneyAndAmount = ConsoleHelper.getValidTwoDigits(currencyCode);
        try
        {
            int k = Integer.parseInt(moneyAndAmount[0]);
            int l = Integer.parseInt(moneyAndAmount[1]);
            currencyManipulator.addAmount(k, l);
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), k * l, currencyCode));
        } catch (NumberFormatException e)
        {
            ConsoleHelper.writeMessage(res.getString("invalid.data"));
        }
    }
}
