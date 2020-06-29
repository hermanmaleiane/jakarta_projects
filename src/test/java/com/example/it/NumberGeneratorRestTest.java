package com.example.it;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.challenge.herman.rest.NumberGeneratorRest;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.microshed.testing.jaxrs.RESTClient;
import org.microshed.testing.jupiter.MicroShedTest;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.ws.rs.core.Response;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicroShedTest
public class NumberGeneratorRestTest {

    @Container
    public static ApplicationContainer app = new ApplicationContainer()
            .withReadinessPath("/")
            .withAppContextRoot("/number-generator/api");

    private static final String BASE_URL = "http://localhost:8080/Trainning/api";

    @RESTClient
    public static NumberGeneratorRest numberGeneratorRest;

    @BeforeAll
    static void init() {
        numberGeneratorRest = JAXRSClientFactory.create(BASE_URL, NumberGeneratorRest.class,
                Collections.singletonList(new JacksonJaxbJsonProvider()));
    }

    @Test
    void testGenerateRandom() {
        assertEquals(Response.Status.OK.getStatusCode(), numberGeneratorRest.generateRandomNumber().getStatus());
    }

    @Test
    void testHistory() {
        assertEquals(Response.Status.OK.getStatusCode(), numberGeneratorRest.history().getStatus());
    }


    @Test
    void testStats() {
        assertEquals(Response.Status.OK.getStatusCode(), numberGeneratorRest.stats().getStatus());
    }

    @Test
    void testPending() {
        assertEquals(Response.Status.OK.getStatusCode(), numberGeneratorRest.pending().getStatus());
    }


}