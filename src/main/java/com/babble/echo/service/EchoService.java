package com.babble.echo.service;

import com.babble.echo.entity.EndpointAttribute;

public interface EchoService {
    EndpointAttribute processEcho(String method, String requestURI);
}
