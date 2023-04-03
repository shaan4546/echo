package com.babble.echo.repository;

import com.babble.echo.entity.EndpointAttribute;
import com.babble.echo.entity.EndpointAttributeResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
class EchoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    EchoRepository mockEchoRepository;

    @Test
    public void testFindByVerbAndPath() {
        EndpointAttribute givenEndpoint = new EndpointAttribute("GET", "/foo",
                        new EndpointAttributeResponse(200, (Map<String, String>) new HashMap<>().put("Content-Type", "application/json"), "Created"));
        givenEndpoint = entityManager.persistAndFlush(givenEndpoint);
        assertThat(mockEchoRepository.findByVerbAndPath(givenEndpoint.getVerb(), givenEndpoint.getPath())).isEqualTo(givenEndpoint);
    }
}