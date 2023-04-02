package com.babble.echo.controller;

import com.babble.echo.entity.Endpoint;
import com.babble.echo.response_handling.CustomResponse;
import com.babble.echo.response_handling.ErrorResponse;
import com.babble.echo.service.EndpointService;
import com.sun.jdi.request.InvalidRequestStateException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController()
class EndpointController {

    @Autowired
    private EndpointService endpointService;

    @PostMapping("/endpoint")
    ResponseEntity createNewEndpoint(@RequestBody Endpoint newEndpoint) {
        if(!newEndpoint.validate()){
            return new ResponseEntity(new ErrorResponse(Arrays.asList(new CustomResponse(HttpStatus.BAD_REQUEST.toString(), "Invalid endpoint request body."))), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(endpointService.save(newEndpoint), HttpStatus.OK);
    }

    @GetMapping("/endpoint")
    List<Endpoint> findAllEndpoints() {
        return endpointService.findAll();
    }

    @PatchMapping("/endpoint/{id}")
    ResponseEntity<Object> patchEndpoint(@PathVariable(name = "id") long id, @RequestBody Endpoint newEndpoint){
        try {
            return new ResponseEntity<Object>(endpointService.patch(id, newEndpoint), HttpStatus.OK);
        }
        catch (InvalidRequestStateException exc) {
            return new ResponseEntity(new ErrorResponse(Arrays.asList(new CustomResponse(HttpStatus.BAD_REQUEST.toString(), "Invalid endpoint request body."))), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(new ErrorResponse(Arrays.asList(new CustomResponse(HttpStatus.NOT_FOUND.toString(), "Requested Endpoint with ID " + id + " does not exist"))), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/endpoint/{id}")
    ResponseEntity<Object> deleteEndpoint(@PathVariable Long id) {
        if(endpointService.delete(id)){
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(new ErrorResponse(Arrays.asList(new CustomResponse(HttpStatus.NOT_FOUND.toString(), "Requested Endpoint with ID " + id + " does not exist"))), HttpStatus.NOT_FOUND);
    }
}