package me.arco.pos;

import me.arco.pos.application.BalanceChecker;
import me.arco.pos.exception.BadJsonException;
import me.arco.pos.exception.UserNotFoundException;
import me.arco.pos.util.PosClient;

/**
 * Created by arnaudcoel on 11/11/15.
 */
public class Runner {
    public static void main(String[] args) {
        BalanceChecker balanceChecker = new BalanceChecker();
    }
}
