package com.ludogorieSoft.villagelifefrontend.exceptions;

public class ReCaptchaInvalidException extends RuntimeException {
    public ReCaptchaInvalidException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ReCaptchaInvalidException(String message) {
        super(message);
    }
}
