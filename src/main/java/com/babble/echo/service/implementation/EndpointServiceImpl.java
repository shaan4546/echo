package com.babble.echo.service.implementation;

import com.babble.echo.entity.Endpoint;
import com.babble.echo.repository.EndpointRepository;
import com.babble.echo.service.EndpointService;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class EndpointServiceImpl implements EndpointService {

    @Autowired
    private EndpointRepository repository;

    @Override
    public Endpoint save(Endpoint newEndpoint) {
        return repository.save(newEndpoint);
    }

    @Override
    public List<Endpoint> findAll() {
        return (List<Endpoint>) repository.findAll();
    }

    @Override
    public boolean delete(Long id) {
        if(!repository.findById(id).isPresent()){
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @Override
    public Endpoint patch(Long id, Endpoint newEndpoint) throws Exception {
        Optional<Endpoint> endpoint =  repository.findById(id);
        if(!endpoint.isPresent()){
            throw new EntityNotFoundException();
        }else{
            try{
                if(newEndpoint.getAttributes().getPath() != null){endpoint.get().getAttributes().setPath(newEndpoint.getAttributes().getPath());}
                if(newEndpoint.getAttributes().getVerb() != null){endpoint.get().getAttributes().setVerb(newEndpoint.getAttributes().getVerb());}
                if(newEndpoint.getAttributes().getResponse().getBody() != null){endpoint.get().getAttributes().getResponse().setBody(newEndpoint.getAttributes().getResponse().getBody());}
                if(newEndpoint.getAttributes().getResponse().getCode() != null){endpoint.get().getAttributes().getResponse().setCode(newEndpoint.getAttributes().getResponse().getCode());}
                if(newEndpoint.getAttributes().getResponse().getHeaders() != null){endpoint.get().getAttributes().getResponse().setHeaders(newEndpoint.getAttributes().getResponse().getHeaders());}
                Endpoint endpointResponse = repository.save(endpoint.get());
                return endpointResponse;
            }catch(Exception e) {
                throw new InvalidRequestStateException();
            }
        }
    }
}
