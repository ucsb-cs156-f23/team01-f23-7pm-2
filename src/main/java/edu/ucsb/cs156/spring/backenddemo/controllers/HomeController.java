package edu.ucsb.cs156.spring.backenddemo.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Tag(name="Home Page with links to documentation")
@Slf4j
@RestController
public class HomeController {
    
    @Operation(summary = "Get general info about the server, including link to api documentation")
    @GetMapping("/")
    public ResponseEntity<String> getHome() throws JsonProcessingException {
        log.info("Home Page accessed");
        ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
        // builder.scheme("http");
        URI uri = builder.build().toUri();

        String body = getHomePageObjectJSON(uri.toString());
        return ResponseEntity.ok().body(body);
    }
    
    public static String getHomePageObjectJSON(String baseUrl) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        List<String> team = Arrays.asList("Andy O.", "Hongrui S.", "Jonathan C.", "Kyle W.", "Richard H.", "Tiger Y.");
        Map<String, Object> resultMap = Map.of(
                "greeting","Greetings from Spring Boot!",
                "team", team,
                "repo","https://github.com/ucsb-cs156-f23/team01-f23-7pm-2",
                "api-documentation", baseUrl + "swagger-ui/index.html"
        );

        return mapper.writeValueAsString(resultMap);
    }
}
