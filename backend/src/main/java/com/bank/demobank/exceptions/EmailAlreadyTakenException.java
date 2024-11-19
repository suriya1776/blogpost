package com.bank.demobank.exceptions;

public class EmailAlreadyTakenException extends RuntimeException {

    public EmailAlreadyTakenException(String message){
        super(message);
    }
    
}
