package com.babble.echo.service.implementation;

import com.babble.echo.entity.EndpointAttribute;
import com.babble.echo.repository.EchoRepository;
import com.babble.echo.service.EchoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EchoServiceImpl implements EchoService {

    Logger logger = LoggerFactory.getLogger(EchoServiceImpl.class);

    @Autowired
    EchoRepository echoRepository;

    @Override
    public EndpointAttribute processEcho(String method, String requestURI) {
        EndpointAttribute endpointAttribute = echoRepository.findByVerbAndPath(method, requestURI);
        logger.info("Fetched echo endpoint with given config. : " + endpointAttribute);
        return echoRepository.findByVerbAndPath(method, requestURI);
    }
}
