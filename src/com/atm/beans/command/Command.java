package com.atm.beans.command;

import com.atm.beans.exception.InterruptOperationException;

/**
 * Created by Alexander on 12.06.2015.
 */
interface Command {
    void execute() throws InterruptOperationException;
}
