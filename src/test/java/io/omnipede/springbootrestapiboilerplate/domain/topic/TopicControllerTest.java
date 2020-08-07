package io.omnipede.springbootrestapiboilerplate.domain.topic;

import static io.omnipede.springbootrestapiboilerplate.utils.ApiDocumentUtils.getDocumentRequest;
import static io.omnipede.springbootrestapiboilerplate.utils.ApiDocumentUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.omnipede.springbootrestapiboilerplate.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

/**
 * MockMvc 를 이용한 topic api controller 테스트
 * @see "https://siyoon210.tistory.com/145"
 * @see "https://www.lesstif.com/java/mockmvc-jsonpath-14090466.html"
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TopicService topicService;

    // Topic을 json으로 바꿀 때 사용하는 mapper
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        // 테스트용 topic entity 두 개 추가.
        topicService.addTopic(new Topic("java", "java topic", "java description"));
        topicService.addTopic(new Topic("spring", "spring topic", "spring description"));
    }

    /**
     * 모든 토픽 반환하는 api를 테스트하는 함수.
     * @throws Exception
     */
    @Test
    public void getAllTopics () throws Exception {
        // URI endpoint of API
        final String endPoint = "/topics";
        mockExpectOk(get(endPoint))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                // Rest docs
                .andDo(document("get-all-topics",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                fieldWithPath("status").description("Http status"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("전체 topic 리스트"),
                                fieldWithPath("data[].id").description("Topic id"),
                                fieldWithPath("data[].name").description("Topic name"),
                                fieldWithPath("data[].description").description("Topic description")
                        )
                ));
    }

    /**
     * 특정 토픽 하나를 반환하는 api를 테스트하는 함수.
     * @throws Exception
     */
    @Test
    public void getTopic () throws Exception {
        // URI endpoint of API
        final String endPoint = "/topics/{id}";
        // API 호출.
        mockExpectOk(RestDocumentationRequestBuilders.get(endPoint, "java"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isMap())
                // Rest docs
                .andDo(document("get-topic",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("찾고자 하는 토픽의 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").description("Http status"),
                                fieldWithPath("data").description("검색한 topic"),
                                fieldWithPath("data.id").description("Topic id"),
                                fieldWithPath("data.name").description("Topic name"),
                                fieldWithPath("data.description").description("Topic description")
                        )
                ));
    }

    @Test
    public void getTopicByQuery () throws Exception {
        // URI endpoint of API
        final String endPoint = "/topics/search?id=java";
        // API 호출.
        mockExpectOk(RestDocumentationRequestBuilders.get(endPoint))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isMap())
                // Rest docs
                .andDo(document("get-topic",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("id").description("찾고자 하는 토픽의 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").description("Http status"),
                                fieldWithPath("data").description("검색한 topic"),
                                fieldWithPath("data.id").description("Topic id"),
                                fieldWithPath("data.name").description("Topic name"),
                                fieldWithPath("data.description").description("Topic description")
                        )
                ));
    }

    /**
     * 토픽이 없을 경우 제대로 에러 처리를 하는지 확인하는 함수
     */
    @Test
    public void notFoundTopic () throws Exception {
        // URI endpoint of API
        final String endPoint = "/topics/{id}";
        // API 호출
        mockMvc.perform(get(endPoint, "python").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.code").value(ErrorCode.RESOURCE_NOT_EXISTS.getCode()))
                .andExpect(jsonPath("$.message").exists());
    }

    /**
     * 토픽을 추가하는 api 를 테스트하는 함수.
     * @throws Exception
     */
    @Test
    public void addTopic () throws Exception {
        // URI endpoint of API
        final String endPoint = "/topics";

        // 새로 추가할 topic 생성하고 문자열로 바꿈
        Topic topicToAdd = new Topic("javascript", "javascript topic", "javascript description");
        String stringFormatOfTopicToAdd = objectMapper.writeValueAsString(topicToAdd);

        // API 호출
        mockExpectOk(post(endPoint), stringFormatOfTopicToAdd)
                // Rest docs
                .andDo(document("add-topic",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("id").description("토픽 아이디"),
                                fieldWithPath("name").description("토픽 이름"),
                                fieldWithPath("description").description("토픽 설명")
                        ),
                        responseFields(
                                fieldWithPath("status").description("Http status")
                        )
                ));
    }

    /**
     * 토픽을 업데이트하는 api를 테스트하는 함수.
     * @throws Exception
     */
    @Test
    public void updateTopic () throws Exception {
        // URI endpoint of API
        final String endPoint = "/topics";

        // 업데이트되기 전의 topic을 받아옴
        Topic oldTopic = topicService.getTopics("java");

        // 업데이트할 topic 생성하고 문자열로 바꿈.
        Topic topicToUpdate = new Topic("java", "Updated java topic", "Updated java description");
        String stringFormatOfTopicToUpdate = objectMapper.writeValueAsString(topicToUpdate);

        // API 호출
        mockExpectOk(put(endPoint), stringFormatOfTopicToUpdate)
                // Rest docs
                .andDo(document("update-topic",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("id").description("토픽 아이디"),
                                fieldWithPath("name").description("토픽 이름"),
                                fieldWithPath("description").description("토픽 설명")
                        ),
                        responseFields(
                                fieldWithPath("status").description("Http status")
                        )
                ));
    }

    /**
     * 토픽을 삭제하는 api를 테스트하는 함수.
     * @throws Exception
     */
    @Test
    public void deleteTopic () throws Exception {
        // URI endpoint of api
        final String endPoint = "/topics/{id}";

        // API 호출
        mockExpectOk(RestDocumentationRequestBuilders.delete(endPoint, "java"))
                // Rest docs
                .andDo(document("delete-topic",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("토픽 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").description("Http status")
                        ))
                );
    }

    // Helper method
    private ResultActions mockExpectOk(MockHttpServletRequestBuilder builder, String content) throws Exception {
        return mockExpectOk(builder.content(content));
    }

    // Helper method
    private ResultActions mockExpectOk(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(200));
    }
}
