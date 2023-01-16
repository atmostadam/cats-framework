package com.atmostadam.cats.framework.jpa;

import com.atmostadam.cats.framework.configuration.CatConfigurationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.atmostadam.cats.framework.data.CatTestValues.catEntityTestData;
import static com.atmostadam.cats.framework.data.CatTestValues.randomCatEntityTestData;


@SpringJUnitConfig(CatConfigurationTest.class)
class CatRepositoryTest {
    @Autowired
    CatRepository repository;

    @Test
    void queryByMicrochipNumber() {
        repository.querySingleRowByMicrochipNumber(123456789000001L);
    }

    @Test
    void insertSingleRow() {
        repository.insertSingleRow(randomCatEntityTestData());
    }

    @Test
    void updateSingleRow() {
        repository.updateSingleRow(catEntityTestData());
    }

    @Test
    void deleteSingleRow() {
        repository.deleteSingleRow(catEntityTestData());
    }
}
