package com.babble.echo.repository;

import com.babble.echo.entity.Endpoint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndpointRepository extends CrudRepository<Endpoint, Long> {
}
