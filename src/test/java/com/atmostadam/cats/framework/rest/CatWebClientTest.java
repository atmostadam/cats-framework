package com.atmostadam.cats.framework.rest;

import com.atmostadam.cats.framework.configuration.CatTestConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(CatTestConfiguration.class)
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
