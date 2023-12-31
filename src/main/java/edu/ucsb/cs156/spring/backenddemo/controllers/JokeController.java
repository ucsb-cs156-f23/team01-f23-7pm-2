package edu.ucsb.cs156.spring.backenddemo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.ucsb.cs156.spring.backenddemo.services.JokeQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Jokes from https://v2.jokeapi.dev/joke/")
@RestController
@RequestMapping("/api/jokes")
public class JokeController {

    @Autowired
    JokeQueryService jokeQueryService;

    @Operation(summary = "Get a list of jokes")
    @GetMapping("/get")
    public ResponseEntity<String> get(
            @RequestParam String category,
            @RequestParam int numJokes
    ) throws JsonProcessingException {
        String result = jokeQueryService.getJSON(category, numJokes);
        return ResponseEntity.ok().body(result);
    }
}
