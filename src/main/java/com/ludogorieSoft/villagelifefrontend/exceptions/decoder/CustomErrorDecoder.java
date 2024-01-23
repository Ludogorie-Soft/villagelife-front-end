package com.ludogorieSoft.villagelifefrontend.exceptions.decoder;

import com.ludogorieSoft.villagelifefrontend.exceptions.*;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CustomErrorDecoder implements ErrorDecoder {
    ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        String message = extractErrorMessage(response);
        return switch (response.status()) {
            case 417 -> throw new NoConsentException(message);
            case 400 -> throw new ApiRequestException(message);
            case 403 -> throw new AccessDeniedException("Token expired or invalid");
            case 401 -> throw new AccessDeniedException(message);
            case 408 -> throw new TokenExpiredException(message);
            case 409 -> throw new DuplicateEmailException(message);
            default -> errorDecoder.decode(methodKey, response);
        };
    }

    private String extractErrorMessage(Response response) {
        try {
            if (response.body() != null) {
                InputStream body = response.body().asInputStream();
                String errorMessage = IOUtils.toString(body, StandardCharsets.UTF_8.toString());
                return errorMessage.trim();
            }

            } catch(IOException exception){
                return new IOException(exception.getMessage()).toString();

            }
        return "Error extracting message!";
    }
}
