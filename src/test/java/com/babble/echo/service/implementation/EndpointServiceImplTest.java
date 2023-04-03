package com.babble.echo.service.implementation;

import com.babble.echo.entity.Endpoint;
import com.babble.echo.entity.EndpointAttribute;
import com.babble.echo.entity.EndpointAttributeResponse;
import com.babble.echo.exception.NoRecordFound;
import com.babble.echo.repository.EndpointRepository;
import com.babble.echo.service.EndpointService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EndpointServiceImplTest {

    @Mock
    EndpointRepository mockEndpointRepository;

    @InjectMocks
    EndpointServiceImpl mockEndpointServiceImpl;

    @Test
    void testShouldSaveNewEndpoint() {
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        when(mockEndpointRepository.save(any())).thenReturn(givenEndpoint);

        Endpoint actualEndpoint = mockEndpointServiceImpl.save(givenEndpoint);

        assertAll(
                () -> assertNotNull(actualEndpoint),
                () -> assertEquals(givenEndpoint, actualEndpoint)
        );
    }

    @Test
    void testShouldReturnExistingEndpointForGivenPathAndVerb() {
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        when(mockEndpointRepository.findByAttributes_Verb_AndAttributes_Path(any(), any())).thenReturn(givenEndpoint);
        Endpoint actualEndpoint = mockEndpointServiceImpl.save(givenEndpoint);

        assertAll(
                () -> assertNotNull(actualEndpoint),
                () -> assertEquals(givenEndpoint, actualEndpoint)
        );
    }

    @Test
    void testShouldReturnEndpointsList() {
        List<Endpoint> givenEndpointList = Arrays.asList(new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created"))));
        when(mockEndpointRepository.findAll()).thenReturn(givenEndpointList);
        List<Endpoint> actualEndpointList = mockEndpointServiceImpl.findAll();

        assertAll(
                () -> assertNotNull(actualEndpointList),
                () -> assertEquals(givenEndpointList, actualEndpointList)
        );
    }

    @Test
    void testShouldDeleteEndpointById() {
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        when(mockEndpointRepository.findById(any())).thenReturn(Optional.of(givenEndpoint));;
        boolean isDeleted = mockEndpointServiceImpl.delete(givenEndpoint.getId());

        assertAll(
                () -> assertEquals(isDeleted, true)
        );
    }

    @Test
    void testShouldReturnFalseForNonExistingEndpointById() {
        when(mockEndpointRepository.findById(any())).thenReturn(Optional.empty());;
        boolean isDeleted = mockEndpointServiceImpl.delete(1L);
        assertAll(
                () -> assertEquals(isDeleted, false)
        );
    }

    @Test
    void testShouldUpdateExistingEndpointById() throws Exception {
        Endpoint givenEndpoint = new Endpoint(1L, "endpoint",
                new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        when(mockEndpointRepository.save(any())).thenReturn(givenEndpoint);
        when(mockEndpointRepository.findById(any())).thenReturn(Optional.of(givenEndpoint));
        Endpoint actualEndpoint = mockEndpointServiceImpl.patch(givenEndpoint.getId(), givenEndpoint);
        assertAll(
                () -> assertNotNull(actualEndpoint),
                () -> assertEquals(givenEndpoint, actualEndpoint)
        );
    }

    @Test
    void testShouldUpdateExistingEndpointByIdd() {
        Executable executable = () -> mockEndpointServiceImpl.patch(1L, new Endpoint());
        assertThrows(NoRecordFound.class, executable);
    }
}