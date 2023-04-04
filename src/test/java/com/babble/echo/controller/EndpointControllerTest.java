package com.babble.echo.controller;

import com.babble.echo.entity.Endpoint;
import com.babble.echo.entity.EndpointAttribute;
import com.babble.echo.entity.EndpointAttributeResponse;
import com.babble.echo.entity.ErrorResponse;
import com.babble.echo.exception.InvalidRequestBody;
import com.babble.echo.exception.NoRecordFound;
import com.babble.echo.service.EndpointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EndpointControllerTest {

    @Mock
    EndpointService mockEndpointService;

    @InjectMocks
    EndpointController mockEndpointController;

    @Test
    void testShouldCreateNewEndpoint() {
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        when(mockEndpointService.save(any())).thenReturn(givenEndpoint);

        Endpoint requestedEndpoint = givenEndpoint;
        ResponseEntity<Endpoint> response = mockEndpointController.createNewEndpoint(requestedEndpoint);
        Endpoint actualEndpoint = (Endpoint) response.getBody();

        assertAll(
                () -> assertNotNull(actualEndpoint),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertEquals(givenEndpoint, actualEndpoint)
        );
    }

    @Test
    void testShouldRaiseBadRequestError() {
        Endpoint givenEndpoint = new Endpoint(1L, "endpoints",
                new EndpointAttribute(1L, "GET", null,
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));

        Endpoint requestedEndpoint = givenEndpoint;
        ResponseEntity<ErrorResponse> response = mockEndpointController.createNewEndpoint(requestedEndpoint);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();

        assertAll(
                () -> assertNotNull(errorResponse),
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode())
        );
    }

    @Test
    void testShouldReturnTheExistingEndpointForExistingVerbAndPath() {
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        mockEndpointService.save(givenEndpoint);
        when(mockEndpointService.save(givenEndpoint)).thenReturn(givenEndpoint);

        Endpoint requestedEndpoint = givenEndpoint;
        ResponseEntity<Endpoint> response = mockEndpointController.createNewEndpoint(requestedEndpoint);
        Endpoint actualEndpoint = (Endpoint) response.getBody();

        assertAll(
                () -> assertNotNull(actualEndpoint),
                () -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> assertEquals(givenEndpoint.getId(), actualEndpoint.getId())
        );
    }

    @Test
    void testShouldReturnExistingEndpoints() {
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        List<Endpoint> givenEndpointList = new ArrayList<Endpoint>();
        givenEndpointList.add(givenEndpoint);

        when(mockEndpointService.findAll()).thenReturn(givenEndpointList);

        ResponseEntity response = mockEndpointController.findAllEndpoints();
        List<Endpoint> actualEndpointList = (List<Endpoint>) response.getBody();

        assertAll(
                () -> assertNotNull(actualEndpointList),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(givenEndpointList, actualEndpointList)
        );
    }

    @Test
    void testShouldReturnEmptyListForNoEndpoints() {
        List<Endpoint> givenEndpointList = new ArrayList<Endpoint>();
        when(mockEndpointService.findAll()).thenReturn(givenEndpointList);

        ResponseEntity response = mockEndpointController.findAllEndpoints();
        List<Endpoint> actualEndpointList = (List<Endpoint>) response.getBody();

        assertAll(
                () -> assertNotNull(actualEndpointList),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(givenEndpointList, actualEndpointList)
        );
    }

    @Test
    void testShouldDeleteEndpointById() {
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        when(mockEndpointService.delete(any())).thenReturn(true);
        ResponseEntity response = mockEndpointController.deleteEndpoint(givenEndpoint.getId());
        assertAll(
                () -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode())
        );
    }

    @Test
    void testShouldRaiseErrorForDeletingNonExistingEndpoint() {
        when(mockEndpointService.delete(any())).thenReturn(false);
        ResponseEntity response = mockEndpointController.deleteEndpoint(2L);
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    void testShouldPatchExistingEndpoint() throws Exception {
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        mockEndpointService.save(givenEndpoint);
        givenEndpoint.getAttributes().setPath("/bar");
        when(mockEndpointService.patch(givenEndpoint.getId(), givenEndpoint)).thenReturn(givenEndpoint);

        Endpoint requestedEndpoint = givenEndpoint;
        ResponseEntity<Endpoint> response = mockEndpointController.patchEndpoint(requestedEndpoint.getId(), requestedEndpoint);
        Endpoint actualEndpoint = (Endpoint) response.getBody();

        assertAll(
                () -> assertNotNull(actualEndpoint),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertEquals(givenEndpoint, actualEndpoint)
        );
    }

    @Test
    void testShouldRaiseNotFoundExceptionForNonExistingEndpoint() throws Exception {
        Endpoint requestedEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        when(mockEndpointService.patch(requestedEndpoint.getId(), requestedEndpoint)).thenThrow(new NoRecordFound());
        ResponseEntity response = mockEndpointController.patchEndpoint(requestedEndpoint.getId(), requestedEndpoint);
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }

    @Test
    void testShouldRaiseInvalidRequestBodyExceptionForInvalidEndpointRequest() throws Exception {
        Endpoint requestedEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", null,
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        when(mockEndpointService.patch(requestedEndpoint.getId(), requestedEndpoint)).thenThrow(new InvalidRequestBody());
        ResponseEntity response = mockEndpointController.patchEndpoint(requestedEndpoint.getId(), requestedEndpoint);
        assertAll(
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode())
        );
    }
}