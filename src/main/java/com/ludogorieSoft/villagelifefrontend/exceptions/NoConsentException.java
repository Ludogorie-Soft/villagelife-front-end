package com.ludogorieSoft.villagelifefrontend.exceptions;


import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
public class NoConsentException extends RuntimeException{
    private static final int STATUS_CODE = HttpServletResponse.SC_EXPECTATION_FAILED;
    public NoConsentException(String message){
        super(message) ;
    }
}
