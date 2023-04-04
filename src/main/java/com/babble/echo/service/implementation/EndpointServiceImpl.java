package com.babble.echo.service.implementation;

import com.babble.echo.entity.Endpoint;
import com.babble.echo.exception.InvalidRequestBody;
import com.babble.echo.exception.NoRecordFound;
import com.babble.echo.repository.EndpointRepository;
import com.babble.echo.service.EndpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EndpointServiceImpl implements EndpointService {

    Logger logger = LoggerFactory.getLogger(EndpointServiceImpl.class);

    @Autowired
    private EndpointRepository repository;

    @Override
    public Endpoint save(Endpoint newEndpoint) {
        logger.info("Try to fetching endpoint by given verb "+ newEndpoint.getAttributes().getVerb()+" and path " + newEndpoint.getAttributes().getPath());
        Endpoint existingEndpoint = repository.findByAttributes_Verb_AndAttributes_Path(newEndpoint.getAttributes().getVerb(), newEndpoint.getAttributes().getPath());
        if(existingEndpoint != null){
            logger.warn("Endpoint already exists for given verb "+ newEndpoint.getAttributes().getVerb()+" and path " + newEndpoint.getAttributes().getPath() + "endpoint :" + existingEndpoint);
            return existingEndpoint;
        }
        logger.info("No records present, creating new endpoint : " + newEndpoint);
        return repository.save(newEndpoint);
    }

    @Override
    public List<Endpoint> findAll() {
        List<Endpoint> endpointList = (List<Endpoint>) repository.findAll();
        logger.info("Fetched given endpoint list : " + endpointList);
        return endpointList;
    }

    @Override
    public boolean delete(Long id) {
        if(!repository.findById(id).isPresent()){
            logger.error("No record present to delete given endpoint with id " + id);
            return false;
        }
        repository.deleteById(id);
        logger.info("Endpoint present by id " + id + "deleted successfully");
        return true;
    }

    @Override
    public Endpoint patch(Long id, Endpoint newEndpoint) throws Exception {
        logger.info("Fetch existing endpoint by id: " + id);
        Optional<Endpoint> endpoint =  repository.findById(id);
        if(!endpoint.isPresent()){
            logger.error("No endpoint found for given id: " + id);
            throw new NoRecordFound();
        }else{
            try{
                if(newEndpoint.getAttributes().getPath() != null){endpoint.get().getAttributes().setPath(newEndpoint.getAttributes().getPath());}
                if(newEndpoint.getAttributes().getVerb() != null){endpoint.get().getAttributes().setVerb(newEndpoint.getAttributes().getVerb());}
                if(newEndpoint.getAttributes().getResponse().getBody() != null){endpoint.get().getAttributes().getResponse().setBody(newEndpoint.getAttributes().getResponse().getBody());}
                if(newEndpoint.getAttributes().getResponse().getCode() != null){endpoint.get().getAttributes().getResponse().setCode(newEndpoint.getAttributes().getResponse().getCode());}
                if(newEndpoint.getAttributes().getResponse().getHeaders() != null){endpoint.get().getAttributes().getResponse().setHeaders(newEndpoint.getAttributes().getResponse().getHeaders());}
                Endpoint endpointResponse = repository.save(endpoint.get());
                logger.info("Endpoint patched successfully: " + endpointResponse);
                return endpointResponse;
            }catch(Exception e) {
                logger.error("Invalid patch endpoint body for given id : " + id);
                throw new InvalidRequestBody();
            }
        }
    }
}
