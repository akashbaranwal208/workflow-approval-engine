package com.workflow.approval.exception;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message)
    {
        super(message);
    }
}
