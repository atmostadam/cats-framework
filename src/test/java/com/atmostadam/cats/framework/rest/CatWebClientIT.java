package com.atmostadam.cats.framework.rest;

import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.framework.configuration.CatTestConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.TimeToLive;
import org.mockserver.matchers.Times;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.atmostadam.cats.framework.data.CatTestValues.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;

@SpringJUnitConfig(CatTestConfiguration.class)
class CatWebClientIT {
    ObjectMapper om = new ObjectMapper();
    private static ClientAndServer mockServer;

    @Autowired
    CatWebClient service;

    @BeforeAll
    static void startMockServer() {
        mockServer = startClientAndServer(1080);
    }

    @AfterAll
    static void stopMockServer() {
        mockServer.stop();
    }

    @Test
    void postHttpStatus200() throws Exception {
        new MockServerClient("localhost", 1080)
                .when(request().withPath("/test").withMethod("POST"),
                        Times.unlimited(),
                        TimeToLive.exactly(TimeUnit.SECONDS, 60L),
                        -60)
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withHeaders(new org.mockserver.model.Header("Content-Type", "application/json; charset=utf-8"))
                                .withBody(om.writeValueAsString(responseEntityTestData())));

        ResponseEntity<CatResponse> response =
                service.invoke(catWebClientPropertiesTestData(), UUID.randomUUID().toString(), catRequestTestData());

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
