package com.bank.demobank.exceptions;

public class UsernameAlreadyTakenException  extends RuntimeException{

    public UsernameAlreadyTakenException(String message){
   super(message);
    }
    
}
