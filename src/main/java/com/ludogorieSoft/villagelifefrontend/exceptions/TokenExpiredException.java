package com.ludogorieSoft.villagelifefrontend.exceptions;


public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(String message) {
        super(message);
    }
}
