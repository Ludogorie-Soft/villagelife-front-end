package com.ludogorieSoft.villagelifefrontend.exceptions;


import lombok.Getter;

import javax.servlet.http.HttpServletResponse;

@Getter
public class ApiRequestException extends RuntimeException{
    private static final int STATUS_CODE = HttpServletResponse.SC_BAD_REQUEST;
    public ApiRequestException(String message) {
        super(message);
    }
}
