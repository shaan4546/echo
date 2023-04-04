package com.babble.echo.controller;

import com.babble.echo.entity.EndpointAttribute;
import com.babble.echo.helper.RequestHandle;
import com.babble.echo.service.EchoService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.babble.echo.helper.Constants.REQUESTED_PAGE_DOES_NOT_EXIST;
import static com.babble.echo.helper.Utils.format;
import static com.babble.echo.helper.Utils.getHeaders;

@RestController()
class EchoController {

    Logger logger = LoggerFactory.getLogger(EchoController.class);
    @Autowired
    private EchoService echoService;

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH})
    ResponseEntity<Object> echoRequest(HttpServletRequest request){
        logger.info("Incoming echo endpoint request, with request method : "+ request.getMethod()+ " and with request uri: "+ request.getRequestURI());
        EndpointAttribute endpointAttribute = echoService.processEcho(request.getMethod(), request.getRequestURI());
        if( endpointAttribute != null){
            return ResponseEntity
                    .status(endpointAttribute.getResponse().getCode())
                    .headers(getHeaders(endpointAttribute.getResponse().getHeaders()))
                    .body(endpointAttribute.getResponse().getBody());
        }
        logger.error("No page found for incoming echo endpoint request,, with request method : "+ request.getMethod()+ " and with request uri: "+ request.getRequestURI());
        return new ResponseEntity(RequestHandle.handleEntityNotFound(format(REQUESTED_PAGE_DOES_NOT_EXIST, request.getRequestURI())), HttpStatus.NOT_FOUND);
    }

}
