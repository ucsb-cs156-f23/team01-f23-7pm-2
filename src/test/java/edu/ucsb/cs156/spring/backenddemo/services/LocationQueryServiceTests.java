package edu.ucsb.cs156.spring.backenddemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;

@RestClientTest(LocationQueryService.class)
public class LocationQueryServiceTests {
    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private LocationQueryService locationQueryService;

    @Test
    public void test_getJSON() {

        String location = "Isla_Vista";
        String expectedURL = LocationQueryService.ENDPOINT.replace("{location}", location);

        String fakeJsonResult = "[{\"place_id\":309452706,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright\",\"osm_type\":\"relation\",\"osm_id\":8280709,\"lat\":\"34.4115125\",\"lon\":\"-119.8562131\",\"class\":\"boundary\",\"type\":\"administrative\",\"place_rank\":16,\"importance\":0.37443815291409516,\"addresstype\":\"town\",\"name\":\"Isla Vista\",\"display_name\":\"Isla Vista, Santa Barbara County, California, 93106, United States\",\"boundingbox\":[\"34.4047282\",\"34.4243150\",\"-119.8814672\",\"-119.8371240\"]}]";

        this.mockRestServiceServer.expect(requestTo(expectedURL))
                .andExpect(header("Accept", MediaType.APPLICATION_JSON.toString()))
                .andExpect(header("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andRespond(withSuccess(fakeJsonResult, MediaType.APPLICATION_JSON));

        String actualResult = locationQueryService.getJSON(location);
        assertEquals(fakeJsonResult, actualResult);
    }
}
