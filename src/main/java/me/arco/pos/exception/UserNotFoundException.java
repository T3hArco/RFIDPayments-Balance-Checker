package me.arco.pos.exception;

/**
 * Created by arnaudcoel on 11/11/15.
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("The user was not found");
    }
}
