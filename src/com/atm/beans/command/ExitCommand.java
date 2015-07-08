package com.atm.beans.command;

import com.atm.beans.exception.InterruptOperationException;
import com.atm.beans.CashMachine;
import com.atm.beans.ConsoleHelper;

import java.util.ResourceBundle;

/**
 * Created by Alexander on 12.06.2015.
 */
class ExitCommand implements Command {

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "exit_en"); //13

    @Override
    public void execute() throws InterruptOperationException {

        String answer = "";
        ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));
        answer = ConsoleHelper.readString();

        if (answer.equals(res.getString("yes"))){
            ConsoleHelper.writeMessage(res.getString("thank.message"));
        }
    }
}
