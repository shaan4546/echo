package com.babble.echo.service;

import com.babble.echo.entity.Endpoint;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EndpointService {
    Endpoint save(Endpoint newEndpoint);

    List<Endpoint> findAll();

    boolean delete(Long id);

    Endpoint patch(Long id, Endpoint newEndpoint) throws Exception;
}
