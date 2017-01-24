package me.arco.pos.exception;

/**
 * Created by arnaudcoel on 11/11/15.
 */
public class BadJsonException extends Exception {
    public BadJsonException() {
        super("The JSON string was bad");
    }
}
