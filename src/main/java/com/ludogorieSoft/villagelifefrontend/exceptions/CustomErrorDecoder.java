package com.ludogorieSoft.villagelifefrontend.exceptions;

//import feign.Response;
//import feign.codec.ErrorDecoder;
//
//public class CustomErrorDecoder implements ErrorDecoder {
//    @Override
//    public Exception decode(String methodKey, Response response) {
//        if (response.status() == 409) {
//            throw new RuntimeException("Duplicate entry error");
//        }
//        return new ErrorDecoder.Default().decode(methodKey, response);
//    }
//}
//