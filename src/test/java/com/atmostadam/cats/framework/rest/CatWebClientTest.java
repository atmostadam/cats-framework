package com.atmostadam.cats.framework.rest;

import com.atmostadam.cats.framework.configuration.CatConfigurationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(CatConfigurationTest.class)
public class CatWebClientTest {
    @Autowired
    CatWebClient service;

    @Test
    void httpStatus200() {
        /*
        String requestId = UUID.randomUUID().toString();
        CatRequest request = new CatRequest();

        ResponseEntity<CatResponse> response =
                service.invoke("postAdoptAPetCat", requestId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        */
    }
}
