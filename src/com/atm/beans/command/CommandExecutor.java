package com.atm.beans.command;

import com.atm.beans.Operation;
import com.atm.beans.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;


public class CommandExecutor {

    private CommandExecutor(){}

    private static Map<Operation, Command> map = new HashMap<>();

    static {
        map.put(Operation.LOGIN, new LoginCommand());
        map.put(Operation.INFO, new InfoCommand());
        map.put(Operation.DEPOSIT, new DepositCommand());
        map.put(Operation.WITHDRAW, new WithdrawCommand());
        map.put(Operation.EXIT, new ExitCommand());
    }

    public static final void execute(Operation operation) throws InterruptOperationException {
        map.get(operation).execute();
    }
}