package com.babble.echo.controller;

import com.babble.echo.entity.Endpoint;
import com.babble.echo.entity.EndpointAttribute;
import com.babble.echo.entity.EndpointAttributeResponse;
import com.babble.echo.service.EchoService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static com.babble.echo.helper.Utils.getHeaders;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EchoControllerTest {

    @Mock
    EchoService mockEchoService;

    @InjectMocks
    EchoController mockEchoController;

    @Test
    void testShouldEchoRegisteredGetEndpoint() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, headers, "Fetched successfully")));
        when(mockEchoService.processEcho(givenEndpoint.getAttributes().getVerb(), givenEndpoint.getAttributes().getPath())).thenReturn(givenEndpoint.getAttributes());


        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getMethod()).thenReturn(givenEndpoint.getAttributes().getVerb());
        when(mockRequest.getRequestURI()).thenReturn(givenEndpoint.getAttributes().getPath());

        ResponseEntity response = mockEchoController.echoRequest(mockRequest);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(givenEndpoint.getAttributes().getResponse().getCode(), response.getStatusCode().value()),
                () -> assertEquals(givenEndpoint.getAttributes().getResponse().getBody(), response.getBody()),
                () -> assertEquals(getHeaders(givenEndpoint.getAttributes().getResponse().getHeaders()), response.getHeaders())
        );
    }

    @Test
    void testShouldEchoRegisteredDeleteEndpoint() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "DELETE", "/bar/1",
                        new EndpointAttributeResponse(1L, HttpStatus.NO_CONTENT.value(), headers, "Deleted Successfully")));
        when(mockEchoService.processEcho(givenEndpoint.getAttributes().getVerb(), givenEndpoint.getAttributes().getPath())).thenReturn(givenEndpoint.getAttributes());


        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getMethod()).thenReturn(givenEndpoint.getAttributes().getVerb());
        when(mockRequest.getRequestURI()).thenReturn(givenEndpoint.getAttributes().getPath());

        ResponseEntity response = mockEchoController.echoRequest(mockRequest);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(givenEndpoint.getAttributes().getResponse().getCode(), response.getStatusCode().value()),
                () -> assertEquals(givenEndpoint.getAttributes().getResponse().getBody(), response.getBody()),
                () -> assertEquals(getHeaders(givenEndpoint.getAttributes().getResponse().getHeaders()), response.getHeaders())
        );
    }

    @Test
    void testShouldEchoRegisteredPostEndpoint() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "POST", "/bar",
                        new EndpointAttributeResponse(1L, HttpStatus.OK.value(), headers, "Created Successfully")));
        when(mockEchoService.processEcho(givenEndpoint.getAttributes().getVerb(), givenEndpoint.getAttributes().getPath())).thenReturn(givenEndpoint.getAttributes());


        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getMethod()).thenReturn(givenEndpoint.getAttributes().getVerb());
        when(mockRequest.getRequestURI()).thenReturn(givenEndpoint.getAttributes().getPath());

        ResponseEntity response = mockEchoController.echoRequest(mockRequest);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(givenEndpoint.getAttributes().getResponse().getCode(), response.getStatusCode().value()),
                () -> assertEquals(givenEndpoint.getAttributes().getResponse().getBody(), response.getBody()),
                () -> assertEquals(getHeaders(givenEndpoint.getAttributes().getResponse().getHeaders()), response.getHeaders())
        );
    }

    @Test
    void testShouldEchoRegisteredPatchEndpoint() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "PATCH", "/bar/1",
                        new EndpointAttributeResponse(1L, HttpStatus.OK.value(), headers, "Patched Successfully")));
        when(mockEchoService.processEcho(givenEndpoint.getAttributes().getVerb(), givenEndpoint.getAttributes().getPath())).thenReturn(givenEndpoint.getAttributes());


        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getMethod()).thenReturn(givenEndpoint.getAttributes().getVerb());
        when(mockRequest.getRequestURI()).thenReturn(givenEndpoint.getAttributes().getPath());

        ResponseEntity response = mockEchoController.echoRequest(mockRequest);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(givenEndpoint.getAttributes().getResponse().getCode(), response.getStatusCode().value()),
                () -> assertEquals(givenEndpoint.getAttributes().getResponse().getBody(), response.getBody()),
                () -> assertEquals(getHeaders(givenEndpoint.getAttributes().getResponse().getHeaders()), response.getHeaders())
        );
    }

    @Test
    void testShouldRaiseNotFoundExceptionForUnRegisteredEchoEndpoint() {
        when(mockEchoService.processEcho(HttpMethod.GET.toString(), "/foo")).thenReturn(null);

        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getMethod()).thenReturn(HttpMethod.GET.toString());
        when(mockRequest.getRequestURI()).thenReturn("/foo");

        ResponseEntity response = mockEchoController.echoRequest(mockRequest);
        assertAll(
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode())
        );
    }
}