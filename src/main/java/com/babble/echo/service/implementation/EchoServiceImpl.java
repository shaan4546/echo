package com.babble.echo.service.implementation;

import com.babble.echo.entity.EndpointAttribute;
import com.babble.echo.repository.EchoRepository;
import com.babble.echo.repository.EndpointRepository;
import com.babble.echo.service.EchoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EchoServiceImpl implements EchoService {

    @Autowired
    EchoRepository echoRepository;

    @Override
    public EndpointAttribute processEcho(String method, String requestURI) {
        return echoRepository.findByVerbAndPath(method, requestURI);
    }
}
