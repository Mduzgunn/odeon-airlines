package com.odeon.exception;

public class FlightTimeConflictException extends RuntimeException {
    public FlightTimeConflictException(String message) {
        super(message);
    }
} 