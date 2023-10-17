package edu.ucsb.cs156.spring.backenddemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ucsb.cs156.spring.backenddemo.services.JokeQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(value = JokeController.class)
public class JokeControllerTests {
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    JokeQueryService mockJokeQueryService;


    @Test
    public void test_getJokes() throws Exception {
        String fakeJsonResult="""
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
        String category = "Programming";
        int numJokes = 2;
        when(mockJokeQueryService.getJSON(eq(category), eq(numJokes))).thenReturn(fakeJsonResult);

        String url = String.format("/api/jokes/get?category=%s&numJokes=%s", category, numJokes);

        MvcResult response = mockMvc
            .perform( get(url).contentType("application/json"))
            .andExpect(status().isOk()).andReturn();

        String responseString = response.getResponse().getContentAsString();

        assertEquals(fakeJsonResult, responseString);
    }

}