package com.atmostadam.cats.framework.service;

import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.framework.configuration.CatTestConfiguration;
import com.atmostadam.cats.framework.jpa.CatRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.atmostadam.cats.framework.data.CatTestValues.*;
import static com.atmostadam.cats.framework.util.CatUtils.convertToJsonNode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(CatTestConfiguration.class)
public class QueryByMicrochipNumberTest {
    @InjectMocks
    QueryByMicrochipNumberCat service;

    @Mock
    CatRepository repository;

    @Test
    void httpStatus200() {
        when(repository.querySingleRowByMicrochipNumber(isA(Long.class))).thenReturn(catEntityTestData());

        ResponseEntity<CatResponse> actual = service.invoke(TEST_REQUEST_ID, catRequestTestData());

        assertEquals(HttpStatus.OK.value(), actual.getStatusCodeValue());
        assertEquals(TEST_REQUEST_ID, actual.getHeaders().get("requestId").get(0));
        assertEquals(MediaType.APPLICATION_JSON, actual.getHeaders().getContentType());
        assertEquals(convertToJsonNode(
                new CatResponse()
                        .setMessage("Successfully retrieved row with microchip number [431654132132657]")
                        .addCat(catTestData())),
                convertToJsonNode(actual.getBody()));
    }

    @Test
    void httpStatus404() {
        when(repository.querySingleRowByMicrochipNumber(isA(Long.class))).thenReturn(null);

        ResponseEntity<CatResponse> actual = service.invoke(TEST_REQUEST_ID, catRequestTestData());

        assertEquals(HttpStatus.NOT_FOUND.value(), actual.getStatusCodeValue());
        assertEquals(TEST_REQUEST_ID, actual.getHeaders().get("requestId").get(0));
        assertEquals(MediaType.APPLICATION_JSON, actual.getHeaders().getContentType());
        assertEquals(convertToJsonNode(
                new CatResponse().setMessage("Unable to find cat with microchip number [431654132132657]")),
                convertToJsonNode(actual.getBody()));
    }

    @Test
    void httpStatus500() {
        when(repository.querySingleRowByMicrochipNumber(isA(Long.class))).thenThrow(TEST_CAT_EXCEPTION);

        ResponseEntity<CatResponse> actual = service.invoke(TEST_REQUEST_ID, catRequestTestData());

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), actual.getStatusCodeValue());
        assertEquals(TEST_REQUEST_ID, actual.getHeaders().get("requestId").get(0));
        assertEquals(MediaType.APPLICATION_JSON, actual.getHeaders().getContentType());
        assertEquals(convertToJsonNode(
                new CatResponse().setMessage(TEST_MESSAGE)),
                convertToJsonNode(actual.getBody().setStackTrace(null)));
    }
}
