package com.transport.exceptions;

public class DatabaseException extends Exception {
    public DatabaseException() {
        super("Record not existing");
    }
}
