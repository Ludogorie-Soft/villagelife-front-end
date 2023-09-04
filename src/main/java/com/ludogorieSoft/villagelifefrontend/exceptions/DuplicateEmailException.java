package com.ludogorieSoft.villagelifefrontend.exceptions;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String message){
        super(message) ;
    }
}
