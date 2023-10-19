package edu.ucsb.cs156.spring.backenddemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import edu.ucsb.cs156.spring.backenddemo.services.LocationQueryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;


@WebMvcTest(value = LocationController.class)
public class LocationControllerTests {
  private ObjectMapper mapper = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  LocationQueryService mockLocationQueryService;


  @Test
  public void test_getLocation() throws Exception {
  
    String fakeJsonResult="[{\"place_id\":309452706,\"licence\":\"Data © OpenStreetMap contributors, ODbL 1.0. http://osm.org/copyright\",\"osm_type\":\"relation\",\"osm_id\":8280709,\"lat\":\"34.4115125\",\"lon\":\"-119.8562131\",\"class\":\"boundary\",\"type\":\"administrative\",\"place_rank\":16,\"importance\":0.37443815291409516,\"addresstype\":\"town\",\"name\":\"Isla Vista\",\"display_name\":\"Isla Vista, Santa Barbara County, California, 93106, United States\",\"boundingbox\":[\"34.4047282\",\"34.4243150\",\"-119.8814672\",\"-119.8371240\"]}]";
    String location = "Isla_Vista";
    when(mockLocationQueryService.getJSON(eq(location))).thenReturn(fakeJsonResult);

    String url = String.format("/api/locations/get?location=%s",location); // TODO: change format

    MvcResult response = mockMvc
        .perform( get(url).contentType("application/json"))
        .andExpect(status().isOk()).andReturn();

    String responseString = response.getResponse().getContentAsString();

    assertEquals(fakeJsonResult, responseString);
  }

}