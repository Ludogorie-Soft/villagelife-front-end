package com.ludogorieSoft.villagelifefrontend.exceptions;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException(String message){
        super(message) ;
    }
}
