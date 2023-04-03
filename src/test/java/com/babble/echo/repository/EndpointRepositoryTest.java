package com.babble.echo.repository;

import org.junit.runner.RunWith;
import com.babble.echo.entity.Endpoint;
import com.babble.echo.entity.EndpointAttribute;
import com.babble.echo.entity.EndpointAttributeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class EndpointRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    EndpointRepository mockEndpointRepository;

    @Test
    void testFindByAttributesVerbAndAttributesPath() {
        Endpoint givenEndpoint = new Endpoint("endpoints",
                new EndpointAttribute("GET", "/foo",
                        new EndpointAttributeResponse(200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created")));
        entityManager.persistAndFlush(givenEndpoint);
        assertThat(mockEndpointRepository.findByAttributes_Verb_AndAttributes_Path(givenEndpoint.getAttributes().getVerb(), givenEndpoint.getAttributes().getPath())).isEqualTo(givenEndpoint);
    }
}