package com.babble.echo.service.implementation;

import com.babble.echo.entity.Endpoint;
import com.babble.echo.entity.EndpointAttribute;
import com.babble.echo.entity.EndpointAttributeResponse;
import com.babble.echo.repository.EchoRepository;
import com.babble.echo.repository.EndpointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EchoServiceImplTest {

    @Mock
    EchoRepository mockEchoRepository;

    @InjectMocks
    EchoServiceImpl mockEchoServiceImpl;

    @Test
    void testProcessEcho() {
        EndpointAttribute givenEndpointAttribute = new EndpointAttribute(1L, "GET", "/foo",
                        new EndpointAttributeResponse(1L, 200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created"));
        when(mockEchoRepository.findByVerbAndPath(any(), any())).thenReturn(givenEndpointAttribute);

        EndpointAttribute actualEndpointAttribute = mockEchoServiceImpl.processEcho(givenEndpointAttribute.getVerb(), givenEndpointAttribute.getPath());

        assertAll(
                () -> assertNotNull(actualEndpointAttribute),
                () -> assertEquals(givenEndpointAttribute, actualEndpointAttribute)
        );
    }
}