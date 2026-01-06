package com.policy_service.exception;


public class PolicyOperationException extends RuntimeException {
    public PolicyOperationException(String message) {
        super(message);
    }
}

