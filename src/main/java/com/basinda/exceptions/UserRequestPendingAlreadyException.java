package com.basinda.exceptions;

public class UserRequestPendingAlreadyException extends RuntimeException {

    public UserRequestPendingAlreadyException(){

    }
    public UserRequestPendingAlreadyException(String message){
        super(message);
    }
}