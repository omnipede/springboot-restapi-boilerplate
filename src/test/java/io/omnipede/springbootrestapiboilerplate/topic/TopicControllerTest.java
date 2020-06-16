package io.omnipede.springbootrestapiboilerplate.topic;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.omnipede.springbootrestapiboilerplate.exception.BusinessException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

/**
 * MockMvc 를 이용한 topic api controller 테스트
 * @see "https://siyoon210.tistory.com/145"
 * @see "https://www.lesstif.com/java/mockmvc-jsonpath-14090466.html"
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TopicService topicService;


    // Topic을 json으로 바꿀 때 사용하는 mapper
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void before() throws Exception {
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
        // API call
        mockHelper(get(endPoint))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isArray())
                // data 필드에 topic 두 개가 들어있어야 함.
                .andExpect(jsonPath("$.data[0]").exists())
                .andExpect(jsonPath("$.data[1]").exists());
    }

    /**
     * 특정 토픽 하나를 반환하는 api를 테스트하는 함수.
     * @throws Exception
     */
    @Test
    public void getTopic () throws Exception {
        // URI endpoint of API
        final String endPoint = "/topics/java";
        // API 호출.
        mockHelper(get(endPoint))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data").isMap());
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
        mockHelper(post(endPoint), stringFormatOfTopicToAdd);

        // 새로 추가된 topic 을 topic service에서 받아온다.
        Topic addedTopic = topicService.getTopics(topicToAdd.getId());
        String stringFormatOfAddedTopic = objectMapper.writeValueAsString(addedTopic);

        // 새로 추가하려고 했던 topic과 새로 추가할 topic이 일치하는지 확인.
        assertEquals(stringFormatOfAddedTopic, stringFormatOfTopicToAdd);
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
        mockHelper(put(endPoint), stringFormatOfTopicToUpdate);

        // 업데이트된 topic을 불러옴.
        Topic updatedTopic = topicService.getTopics(topicToUpdate.getId());

        // 업데이트 되기 전의 topic과 업데이트 된 후의 topic을 비교.
        // Id는 같고, name과 description은 업데이트 했으므로 달라야 한다.
        assertEquals(oldTopic.getId(), updatedTopic.getId());
        assertThat(oldTopic.getName(), is(not(updatedTopic.getName())));
        assertThat(oldTopic.getDescription(), is(not(updatedTopic.getDescription())));
    }

    /**
     * 토픽을 삭제하는 api를 테스트하는 함수.
     * @throws Exception
     */
    @Test
    public void deleteTopic () throws Exception {
        // URI endpoint of api
        final String endPoint = "/topics/java";

        // 삭제 되기 전 topic을 저장.
        Topic topicBeforeDeletion = topicService.getTopics("java");

        // API 호출
        mockHelper(delete(endPoint));

        // topic이 삭제되었으므로 해당하는 topic을 반환할 수 없음.
        try {
            topicService.getTopics(topicBeforeDeletion.getId());
            // Exception 발생 안하면 테스트 실패.
            assertTrue(false);
        } catch (BusinessException e) {
            // Business exception 발생 시 테스트 성공.
            assertTrue(true);
        }
    }

    @Test
    public void exceptions() throws Exception {
        // Not found url
        mockMvc.perform(get("/wrong/uri"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value(401))
                .andDo(print());
        // Bad request - No arguments
        mockMvc.perform(post("/topics"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(print());
        // Bad request - invalid arguments
        mockMvc.perform(post("/topics").contentType(MediaType.APPLICATION_JSON).content("{\n" + "\"name\": \"java topic\",\n" + "\"description\": \"Simple description\"\n" + "}"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value(403))
                .andDo(print());
    }

    // Helper method
    private ResultActions mockHelper(MockHttpServletRequestBuilder builder, String content) throws Exception {
        return mockHelper(builder.content(content));
    }

    // Helper method
    private ResultActions mockHelper(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.status").value(200))
                .andDo(print());
    }
}
