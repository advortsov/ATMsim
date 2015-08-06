package com.atm.beans;

import com.atm.beans.exception.InterruptOperationException;
import com.atm.beans.command.CommandExecutor;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The main-class which starts the programm and define locale
 */

public class CashMachine {

    public static final String RESOURCE_PATH = "com.atm.beans.resources.";//12

    public static void main(String[] args) {

        Locale.setDefault(Locale.ENGLISH);
        ResourceBundle res = ResourceBundle.getBundle(RESOURCE_PATH + "common_en", Locale.ENGLISH);

        try {
            CommandExecutor.execute(Operation.LOGIN);//12
            Operation operation;

            do {
                ConsoleHelper.writeMessage(res.getString("choose.operation") + " \n" +
                        res.getString("operation.INFO") + ": 1;\n" +
                        res.getString("operation.DEPOSIT") + ": 2;\n" +
                        res.getString("operation.WITHDRAW") + ": 3;\n" +
                        res.getString("operation.EXIT") + ": 4");
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            }
            while (operation != Operation.EXIT);

        } catch (InterruptOperationException e) {
            ConsoleHelper.printExitMessage();
        }
    }
}

