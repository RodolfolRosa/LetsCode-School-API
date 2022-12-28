package com.study.quarkus.exception;

public class NotAllowedNameException extends RuntimeException {
    public NotAllowedNameException(String msg) {
        super(msg);
    }
}
