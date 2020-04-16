package io.omnipede.springbootrestapiboilerplate.topic.web;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.omnipede.springbootrestapiboilerplate.topic.domain.entity.Topic;
import io.omnipede.springbootrestapiboilerplate.topic.domain.usecase.TopicService;

/**
 * MockMvc 를 이용한 topic api controller 테스트
 * @see https://siyoon210.tistory.com/145
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TopicControllerTest {

	// TODO: 존재하지 않는 url 에 대한 에러 처리 테스트.
	// TODO: Request body가 적절하지 않을 경우 에러 처리 테스트.
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private TopicService topicService;
	
	// Topic을 json으로 바꿀 때 사용하는 mapper
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	@BeforeEach
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
	public void testGetAllTopicsApi () throws Exception {
		// URI endpoint of API
		final String apiEndPoint = "/topics";
		// API 호출.
		mockMvc.perform(get(apiEndPoint))
		// status code 가 200 인지 확인.
		.andExpect(status().isOk())
		// JSON response에 모든 topic entity가 들어있는지 확인.
		.andExpect(jsonPath("$[0].id", is("java")))
		.andExpect(jsonPath("$[1].id", is("spring")));
	}
	
	/**
	 * 특정 토픽 하나를 반환하는 api를 테스트하는 함수.
	 * @throws Exception
	 */
	@Test
	public void testGetTopicApi () throws Exception {
		// URI endpoint of API
		final String apiEndPoint = "/topics/java";
		// API 호출.
		mockMvc.perform(get(apiEndPoint))
		// status code 200인지 확인
		.andExpect(status().isOk())
		// JSON response 확인
		.andExpect(jsonPath("$.id", is("java")))
		.andExpect(jsonPath("$.name", is(notNullValue())))
		.andExpect(jsonPath("$.description", is(notNullValue())));
	}
	
	/**
	 * 토픽을 추가하는 api 를 테스트하는 함수.
	 * @throws Exception
	 */
	@Test
	public void testAddTopicApi () throws Exception {
		// URI endpoint of API
		final String apiEndPoint = "/topics";
		
		// 새로 추가할 topic 생성하고 문자열로 바꿈
		Topic topicToAdd = new Topic("javascript", "javascript topic", "javascript description");
		String stringFormatOfTopicToAdd = objectMapper.writeValueAsString(topicToAdd);
		
		// API 호출
		mockMvc.perform(
			post(apiEndPoint)
			.contentType(MediaType.APPLICATION_JSON)
			// 새로 추가할 문자열 형태의 topic을 post content body에 넣어줌.
			.content(stringFormatOfTopicToAdd)
		)
		// status code 200인지 확인
		.andExpect(status().isOk());
		
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
	public void testUpdateTopicApi () throws Exception {
		// URI endpoint of API
		final String apiEndPoint = "/topics";
		
		// 업데이트되기 전의 topic을 저장함.
		Topic oldTopic = topicService.getTopics("java");
		
		// 업데이트할 topic 생성하고 문자열로 바꿈.
		Topic topicToUpdate = new Topic("java", "Updated java topic", "Updated java description");
		String stringFormatOfTopicToUpdate = objectMapper.writeValueAsString(topicToUpdate);
		
		// API 호출
		mockMvc.perform(
			put(apiEndPoint)
			.contentType(MediaType.APPLICATION_JSON)
			// 업데이트할 문자열 형태의 topic을 put content body에 넣어줌.
			.content(stringFormatOfTopicToUpdate)
		)
		// status code 200인지 확인.
		.andExpect(status().isOk());
		
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
	public void testDeleteTopicApi () throws Exception {
		// URI endpoint of api
		final String apiEndPoint = "/topics/java";
		
		// 삭제 되기 전 topic을 저장.
		Topic topicBeforeDeletion = topicService.getTopics("java");
		
		// API 호출
		mockMvc.perform(delete(apiEndPoint))
		.andExpect(status().isOk());
		
		// topic이 삭제되었으므로 해당하는 topic을 반환할 수 없음.
		try {
			topicService.getTopics(topicBeforeDeletion.getId());
			// Exception 발생 안하면 테스트 실패.
			assertTrue(false);
		} catch (java.util.NoSuchElementException e) {
			// NoSuchElementException 발생 시 테스트 성공.
			assertTrue(true);
		}
	}
}
