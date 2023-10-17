package edu.ucsb.cs156.spring.backenddemo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ucsb.cs156.spring.backenddemo.beans.CollegeSubreddit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(JokeQueryService.class)
public class JokeQueryServiceTests {

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private JokeQueryService jokeQueryService;

    @Test
    public void test_getJSON() throws URISyntaxException, UnsupportedEncodingException, JsonProcessingException {
        String category = "Programming";
        int numJokes = 2;

        String expectedURL = JokeQueryService.ENDPOINT
                .replace("{category}", category)
                .replace("{numJokes}", String.valueOf(numJokes));

        String fakeJsonResult = """
                {
                    "error": false,
                    "amount": 2,
                    "jokes": [
                        {
                            "category": "Programming",
                            "type": "single",
                            "joke": "A SQL statement walks into a bar and sees two tables.\\nIt approaches, and asks \\"may I join you?\\"",
                            "flags": {
                                "nsfw": false,
                                "religious": false,
                                "political": false,
                                "racist": false,
                                "sexist": false,
                                "explicit": false
                            },
                            "id": 5,
                            "safe": true,
                            "lang": "en"
                        },
                        {
                            "category": "Programming",
                            "type": "twopart",
                            "setup": "How do you know God is a shitty programmer?",
                            "delivery": "He wrote the OS for an entire universe, but didn't leave a single useful comment.",
                            "flags": {
                                "nsfw": false,
                                "religious": true,
                                "political": false,
                                "racist": false,
                                "sexist": false,
                                "explicit": true
                            },
                            "id": 19,
                            "safe": false,
                            "lang": "en"
                        }
                    ]
                }""";

        this.mockRestServiceServer.expect(requestTo(expectedURL))
                .andExpect(header("Accept", MediaType.APPLICATION_JSON.toString()))
                .andExpect(header("Content-Type", MediaType.APPLICATION_JSON.toString()))
                .andRespond(withSuccess(fakeJsonResult, MediaType.APPLICATION_JSON));

        String actualResult = jokeQueryService.getJSON(category, numJokes);
        assertEquals(fakeJsonResult, actualResult);
    }
}
