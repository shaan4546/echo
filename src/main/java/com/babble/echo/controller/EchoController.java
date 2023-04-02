package com.babble.echo.controller;

import com.babble.echo.entity.Endpoint;
import com.babble.echo.entity.EndpointAttribute;
import com.babble.echo.response_handling.CustomResponse;
import com.babble.echo.response_handling.ErrorResponse;
import com.babble.echo.service.EchoService;
import com.babble.echo.service.EndpointService;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController()
class EchoController {
    @Autowired
    private EchoService echoService;

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    ResponseEntity<Object> patchEndpoint(HttpServletRequest request){
        EndpointAttribute endpointAttribute = echoService.processEcho(request.getMethod(), request.getRequestURI());
        if( endpointAttribute != null){
            return ResponseEntity
                    .status(endpointAttribute.getResponse().getCode())
                    .headers(getHeaders(endpointAttribute.getResponse().getHeaders()))
                    .body(endpointAttribute.getResponse().getBody());
        }
        return new ResponseEntity(new ErrorResponse(Arrays.asList(new CustomResponse(HttpStatus.NOT_FOUND.toString(), "Requested page `"+request.getRequestURI()+"` does not exist"))), HttpStatus.NOT_FOUND);
    }

    private HttpHeaders getHeaders(Map<String, String> headers) {
        HttpHeaders responseHeaders = new HttpHeaders();
        if(headers != null){
            for(Map.Entry<String, String> entry : headers.entrySet()){
                responseHeaders.set(entry.getKey(),
                        entry.getValue());
            }
        }
        return responseHeaders;
    }
}
