package com.nth.mike.exception;
public class DuplicateRecordException extends RuntimeException {
    public DuplicateRecordException(String message) {
        super(message);
    }
}