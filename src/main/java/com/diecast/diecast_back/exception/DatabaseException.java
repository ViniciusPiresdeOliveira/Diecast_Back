package com.diecast.diecast_back.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String msg) {
        super(msg);
    }
}