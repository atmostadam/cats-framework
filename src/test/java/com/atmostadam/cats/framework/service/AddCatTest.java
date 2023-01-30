package com.atmostadam.cats.framework.service;

import com.atmostadam.cats.api.entity.CatEntity;
import com.atmostadam.cats.api.model.in.CatRequest;
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
import static org.mockito.Mockito.doNothing;

@SpringJUnitConfig(CatTestConfiguration.class)
public class AddCatTest {
    @InjectMocks
    AddCat service;

    @Mock
    CatRepository repository;

    @Test
    void httpStatus200() {
        doNothing().when(repository).insertSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> actual = service.invoke(TEST_REQUEST_ID, catRequestTestData());

        assertEquals(HttpStatus.OK.value(), actual.getStatusCodeValue());
        assertEquals(TEST_REQUEST_ID, actual.getHeaders().get("requestId").get(0));
        assertEquals(MediaType.APPLICATION_JSON, actual.getHeaders().getContentType());
        assertEquals(convertToJsonNode(
                new CatResponse()
                        .setMessage("Successfully inserted row with microchip number [431654132132657]")
                        .addCat(catTestData())),
                convertToJsonNode(actual.getBody()));
    }

    @Test
    void httpStatus400ZeroCats() {
        doNothing().when(repository).insertSingleRow(isA(CatEntity.class));

        CatRequest request = catRequestTestData();
        request.getCats().clear();

        ResponseEntity<CatResponse> actual = service.invoke(TEST_REQUEST_ID, request);

        assertEquals(HttpStatus.BAD_REQUEST.value(), actual.getStatusCodeValue());
        assertEquals(TEST_REQUEST_ID, actual.getHeaders().get("requestId").get(0));
        assertEquals(MediaType.APPLICATION_JSON, actual.getHeaders().getContentType());
        assertEquals(convertToJsonNode(
                new CatResponse().setMessage("Client has not provided a cat to Add! Bad Request!")),
                convertToJsonNode(actual.getBody()));
    }

    @Test
    void httpStatus400MultipleCats() {
        doNothing().when(repository).insertSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> actual = service.invoke(TEST_REQUEST_ID,
                catRequestTestData().addCat(catTestData()));

        assertEquals(HttpStatus.BAD_REQUEST.value(), actual.getStatusCodeValue());
        assertEquals(TEST_REQUEST_ID, actual.getHeaders().get("requestId").get(0));
        assertEquals(MediaType.APPLICATION_JSON, actual.getHeaders().getContentType());
        assertEquals(convertToJsonNode(
                new CatResponse()
                        .setMessage("Client has provided multiple cats to Add! Bad Request!")
                        .addCat(catTestData())
                        .addCat(catTestData())),
                convertToJsonNode(actual.getBody()));
    }
}
