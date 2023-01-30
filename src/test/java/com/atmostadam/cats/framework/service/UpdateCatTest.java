package com.atmostadam.cats.framework.service;

import com.atmostadam.cats.api.entity.CatEntity;
import com.atmostadam.cats.api.model.out.CatResponse;
import com.atmostadam.cats.framework.configuration.CatTestConfiguration;
import com.atmostadam.cats.framework.jpa.CatRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static com.atmostadam.cats.framework.data.CatTestValues.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@SpringJUnitConfig(CatTestConfiguration.class)
public class UpdateCatTest {
    @InjectMocks
    UpdateCat service;

    @Mock
    CatRepository repository;

    @Test
    void updateSingleRow200() {
        doNothing().when(repository).updateSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> response = service.invoke(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.OK.value()));
        assertEquals(response.getBody().getMessage(), "Successfully updated row with microchip number [431654132132657]");
    }

    @Test
    void updateSingleRow500() {
        doThrow(catRuntimeExceptionTestData()).when(repository).updateSingleRow(isA(CatEntity.class));

        ResponseEntity<CatResponse> response = service.invoke(TEST_REQUEST_ID, catRequestTestData());

        assertThat(response.getHeaders().get("requestId"), Matchers.equalTo(List.of(TEST_REQUEST_ID)));
        assertThat(response.getHeaders().getContentType(), Matchers.equalTo(MediaType.APPLICATION_JSON));

        assertThat(response.getStatusCodeValue(), Matchers.equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        assertEquals(response.getBody().getMessage(), TEST_MESSAGE);
    }
}
