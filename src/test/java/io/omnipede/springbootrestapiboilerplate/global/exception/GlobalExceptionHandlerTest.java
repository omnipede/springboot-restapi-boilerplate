package io.omnipede.springbootrestapiboilerplate.global.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Url not found error
     * @throws Exception
     */
    @Test
    void handleNoHandlerFoundException() throws Exception {
        mockMvc.perform(get("/wrong/uri"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value(404));
    }

    /**
     * Bad request - invalid request body argument
     * @throws Exception
     */
    @Test
    void handleMethodArgumentNotValidException() throws Exception {
        mockMvc.perform(post("/topics").contentType(MediaType.APPLICATION_JSON).content("{\n" + "\"name\": \"java topic\",\n" + "\"description\": \"Simple description\"\n" + "}"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value(403));
    }

    /**
     * Bad request - argument not exists
     * @throws Exception
     */
    @Test
    void handleHttpMessageNotReadableException() throws Exception {
        mockMvc.perform(post("/topics").contentType(MediaType.APPLICATION_JSON).content("{\n" + "\"name\": \"java topic\",\n" + "\"description\": \"Simple description\"\n" + "}"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value(403));
    }
}