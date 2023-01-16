package com.atmostadam.cats.framework.configuration;

import com.atmostadam.cats.api.configuration.CatWebClientProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(CatConfigurationTest.class)
public class CatWebClientPropertiesTest {
    @Autowired
    @Qualifier("CatWebClientPropertiesPetfinder")
    CatWebClientProperties properties;

    @Test
    void injected() {
        assertNotNull(properties);
    }

    @Test
    void url() {
        assertNotNull(properties.getUrl());
        assertEquals("http://localhost:1080/test", properties.getUrl());
    }

    @Test
    void method() {
        assertNotNull(properties.getMethod());
        assertEquals("POST", properties.getMethod());
    }

    @Test
    void username() {
        assertNotNull(properties.getUsername());
        assertEquals("testUsername", properties.getUsername());
    }

    @Test
    void password() {
        assertNotNull(properties.getPassword());
        assertEquals("testPassword", properties.getPassword());
    }

    @Test
    void responseTimeoust() {
        assertNotNull(properties.getResponseTimeout());
        assertEquals(5000, properties.getResponseTimeout());
    }

    @Test
    void connectionTimeout() {
        assertNotNull(properties.getConnectionTimeout());
        assertEquals(5000, properties.getConnectionTimeout());
    }

    @Test
    void readTimeout() {
        assertNotNull(properties.getReadTimeout());
        assertEquals(5000, properties.getReadTimeout());
    }

    @Test
    void writeTimeout() {
        assertNotNull(properties.getWriteTimeout());
        assertEquals(5000, properties.getWriteTimeout());
    }
}
