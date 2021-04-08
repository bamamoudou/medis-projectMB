package com.mediscreen.msclientui.exception;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response res) {

        switch (res.status()){
            case 403 : return new NotAllowedException("Permission denied");
            case 404 : return new NotFoundException("Not found");
            default:
                return defaultErrorDecoder.decode(methodKey, res);
        }

    }

}
