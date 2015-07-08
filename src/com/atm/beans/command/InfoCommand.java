package com.atm.beans.command;


import com.atm.beans.CashMachine;
import com.atm.beans.CurrencyManipulator;
import com.atm.beans.ConsoleHelper;
import com.atm.beans.CurrencyManipulatorFactory;

import java.util.ResourceBundle;

/**
 * Created by Alexander on 12.06.2015.
 */
class InfoCommand implements Command {

    String language = "info_en";
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + language); //13

    @Override
    public void execute() {

        boolean money = false;
        ConsoleHelper.writeMessage(res.getString("before"));

        for (CurrencyManipulator cur : CurrencyManipulatorFactory.getAllCurrencyManipulators()){
            if (cur.hasMoney()){
                if (cur.getTotalAmount() > 0) {
                    ConsoleHelper.writeMessage(cur.getCurrencyCode() + " - " + cur.getTotalAmount());
                    money = true;
                }
            }
        }
        if (!money) {
            ConsoleHelper.writeMessage(res.getString("no.money"));
        }
    }
}
