package com.atmostadam.cats.framework.service;

import com.atmostadam.cats.api.entity.CatEntity;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.framework.jpa.CatRepository;
import org.hamcrest.Matchers;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.atmostadam.cats.framework.data.CatTestValues.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@MockitoSettings
public class CatDatabaseServiceTest {
    @InjectMocks
    CatDatabaseService service;

    @Mock
    CatRepository repository;

    @Test
    void queryByMicrochipNumber200() {
        doReturn(catEntityTestData()).when(repository).querySingleRowByMicrochipNumber(isA(Long.class));

        ResponseEntity<CatResponse> response = service.queryByMicrochipNumber(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.OK.value()));
        assertEquals(response.getBody().getMessage(), "Successfully retrieved row with microchip number [431654132132657]");
    }

    @Test
    void queryByMicrochipNumber404() {
        doReturn(null).when(repository).querySingleRowByMicrochipNumber(isA(Long.class));

        ResponseEntity<CatResponse> response = service.queryByMicrochipNumber(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.NOT_FOUND.value()));
        assertEquals(response.getBody().getMessage(), "Unable to find cat with microchip number [431654132132657]");
    }

    @Test
    void  queryByMicrochipNumber500() {
        doThrow(catRuntimeExceptionTestData()).when(repository).querySingleRowByMicrochipNumber(isA(Long.class));

        ResponseEntity<CatResponse> response = service.queryByMicrochipNumber(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        assertEquals(response.getBody().getMessage(), TEST_MESSAGE);
    }

    @Test
    void insertSingleRow200() {
        doNothing().when(repository).insertSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> response = service.insertSingleRow(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.OK.value()));
        assertEquals(response.getBody().getMessage(), "Successfully inserted row with microchip number [431654132132657]");
    }

    @Test
    void insertSingleRow200cve() {
        doThrow(ConstraintViolationException.class).when(repository).insertSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> response = service.insertSingleRow(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.OK.value()));
        assertEquals(response.getBody().getMessage(), "Successfully inserted row with microchip number [431654132132657]");
    }

    @Test
    void insertSingleRow500() {
        doThrow(catRuntimeExceptionTestData()).when(repository).insertSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> response = service.insertSingleRow(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        assertEquals(response.getBody().getMessage(), TEST_MESSAGE);
    }

    @Test
    void updateSingleRow200() {
        doNothing().when(repository).updateSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> response = service.updateSingleRow(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.OK.value()));
        assertEquals(response.getBody().getMessage(), "Successfully updated row with microchip number [431654132132657]");
    }

    @Test
    void updateSingleRow500() {
        doThrow(catRuntimeExceptionTestData()).when(repository).updateSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> response = service.updateSingleRow(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        assertEquals(response.getBody().getMessage(), TEST_MESSAGE);
    }

    @Test
    void deleteSingleRow200() {
        doReturn(catEntityTestData()).when(repository).querySingleRowByMicrochipNumber(isA(Long.class));
        doNothing().when(repository).deleteSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> response = service.deleteSingleRow(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.OK.value()));
        assertEquals(response.getBody().getMessage(), "Successfully deleted row with microchip number [431654132132657]");
    }

    @Test
    void deleteSingleRow500() {
        doReturn(catEntityTestData()).when(repository).querySingleRowByMicrochipNumber(isA(Long.class));
        doThrow(catRuntimeExceptionTestData()).when(repository).deleteSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> response = service.deleteSingleRow(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        assertEquals(response.getBody().getMessage(), TEST_MESSAGE);
    }
}
