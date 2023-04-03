package com.babble.echo.helper;

import com.babble.echo.entity.ErrorResponseDetails;
import com.babble.echo.entity.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static com.babble.echo.helper.Constants.INVALID_ENDPOINT_REQUEST_BODY;

public class RequestHandle {

    public static ErrorResponse handleInvalidRequest(){
        return new ErrorResponse(
                Arrays.asList(
                        new ErrorResponseDetails(HttpStatus.BAD_REQUEST.toString(),
                                INVALID_ENDPOINT_REQUEST_BODY)));
    }

    public static ErrorResponse handleEntityNotFound(String message){
        return new ErrorResponse(
                Arrays.asList(new ErrorResponseDetails(HttpStatus.NOT_FOUND.toString(),
                        message)));
    }
}
