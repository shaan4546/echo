package com.babble.echo.repository;

import com.babble.echo.entity.Endpoint;
import com.babble.echo.entity.EndpointAttribute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EchoRepository extends CrudRepository<EndpointAttribute, Long> {
    EndpointAttribute findByVerbAndPath(String verb, String path);
}
