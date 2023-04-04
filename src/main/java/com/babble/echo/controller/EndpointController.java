package com.babble.echo.controller;

import com.babble.echo.entity.Endpoint;
import com.babble.echo.exception.InvalidRequestBody;
import com.babble.echo.exception.NoRecordFound;
import com.babble.echo.helper.RequestHandle;
import com.babble.echo.service.EndpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.babble.echo.helper.Constants.REQUESTED_ENDPOINT_WITH_ID_DOES_NOT_EXIST;
import static com.babble.echo.helper.Utils.format;

@RestController()
@RequestMapping("/endpoints")
@PreAuthorize("hasRole('ADMIN')")
class EndpointController {

    Logger logger = LoggerFactory.getLogger(EndpointController.class);

    @Autowired
    private EndpointService endpointService;

    @PostMapping()
    ResponseEntity createNewEndpoint(@RequestBody Endpoint newEndpoint) {
        logger.info("Incoming create endpoint request, with request body : "+ newEndpoint);
        if(!newEndpoint.validate()){
            logger.error("Invalid incoming create endpoint request, with request body : "+ newEndpoint);
            return new ResponseEntity(RequestHandle.handleInvalidRequest(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(endpointService.save(newEndpoint), HttpStatus.CREATED);
    }

    @GetMapping()
    ResponseEntity findAllEndpoints() {
        logger.info("Incoming get all existing endpoints request");
        return new ResponseEntity<Object>(endpointService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteEndpoint(@PathVariable Long id) {
        logger.info("Incoming delete endpoint request, with id : "+ id);
        if(endpointService.delete(id)){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        logger.error("No record found for incoming delete endpoint request, with id : "+ id);
        return new ResponseEntity(RequestHandle.handleEntityNotFound(format(REQUESTED_ENDPOINT_WITH_ID_DOES_NOT_EXIST, Long.toString(id))), HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    ResponseEntity patchEndpoint(@PathVariable(name = "id") long id, @RequestBody Endpoint newEndpoint) {
        logger.info("Incoming patch endpoint request, with id : "+ id + " and request body : "+ newEndpoint);
        return processPathEndpoint(id, newEndpoint);
    }

    private ResponseEntity processPathEndpoint(long id, Endpoint newEndpoint) {
        try {
            return new ResponseEntity<Endpoint>(endpointService.patch(id, newEndpoint), HttpStatus.OK);
        }
        catch (InvalidRequestBody invalidRequestBody) {
            logger.error("Invalid incoming patch endpoint request, with id : "+ id + " and request body : "+ newEndpoint);
            return new ResponseEntity(RequestHandle.handleInvalidRequest(), HttpStatus.BAD_REQUEST);
        } catch (NoRecordFound noRecordFound) {
            logger.error("No record found for incoming patch endpoint request, with id : "+ id + " and request body : "+ newEndpoint);
            return new ResponseEntity(RequestHandle.handleEntityNotFound(format(REQUESTED_ENDPOINT_WITH_ID_DOES_NOT_EXIST, Long.toString(id))), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Exception"+ e.getMessage());
            throw new RuntimeException(e);
        }
    }
}