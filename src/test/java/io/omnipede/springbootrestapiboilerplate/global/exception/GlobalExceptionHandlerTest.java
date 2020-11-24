package io.omnipede.springbootrestapiboilerplate.global.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Url not found error
     */
    @Test
    void handleNoHandlerFoundException() throws Exception {
        mockMvc.perform(get("/wrong/uri"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.code").value(40400));
    }

    /**
     * JSON 형식이 올바르지 않을 경우
     * @throws Exception
     */
    @Test
    void handleHttpmessageNotReadableException () throws Exception {
        String contentString = "{\"memberId\": \"12345\", }";
        mockMvc.perform(post("/purchase")
            .contentType(MediaType.APPLICATION_JSON)
            .content(contentString))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.code").value(40000));
    }

    /**
     * 지원하지 않는 http method 요청시 ex) patch
     */
    @Test
    void handleHttpRequestMethodNotSupportedException() throws Exception {
        mockMvc.perform(patch("/token"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.code").value(40400));
    }
}