package com.bank.demobank.exceptions;

public class UserNotFoundException  extends RuntimeException{

    public UserNotFoundException(String message){
      super(message);
    }
    
}
