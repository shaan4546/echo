package com.babble.echo.controller;

import com.babble.echo.entity.Endpoint;
import com.babble.echo.exception.InvalidRequestBody;
import com.babble.echo.exception.NoRecordFound;
import com.babble.echo.helper.RequestHandle;
import com.babble.echo.service.EndpointService;
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

    @Autowired
    private EndpointService endpointService;

    @PostMapping()
    ResponseEntity createNewEndpoint(@RequestBody Endpoint newEndpoint) {
        if(!newEndpoint.validate()){
            return new ResponseEntity(RequestHandle.handleInvalidRequest(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(endpointService.save(newEndpoint), HttpStatus.CREATED);
    }

    @GetMapping()
    ResponseEntity findAllEndpoints() {
        return new ResponseEntity<Object>(endpointService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteEndpoint(@PathVariable Long id) {
        if(endpointService.delete(id)){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(RequestHandle.handleEntityNotFound(format(REQUESTED_ENDPOINT_WITH_ID_DOES_NOT_EXIST, Long.toString(id))), HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    ResponseEntity patchEndpoint(@PathVariable(name = "id") long id, @RequestBody Endpoint newEndpoint) {
        return processPathEndpoint(id, newEndpoint);
    }

    private ResponseEntity processPathEndpoint(long id, Endpoint newEndpoint) {
        try {
            return new ResponseEntity<Endpoint>(endpointService.patch(id, newEndpoint), HttpStatus.OK);
        }
        catch (InvalidRequestBody invalidRequestBody) {
            return new ResponseEntity(RequestHandle.handleInvalidRequest(), HttpStatus.BAD_REQUEST);
        } catch (NoRecordFound noRecordFound) {
            return new ResponseEntity(RequestHandle.handleEntityNotFound(format(REQUESTED_ENDPOINT_WITH_ID_DOES_NOT_EXIST, Long.toString(id))), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}