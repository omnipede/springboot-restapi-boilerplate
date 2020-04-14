package io.omnipede.springbootrestapiboilerplate.topic.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TopicController {

	// TODO: Swagger를 이용한 API 문서 생
	
	// 모든 토픽 반환.
	@RequestMapping("/topics")
	public String getAllTopics() {
		return "Hello world";
	}
	
	// Id로 토픽을 하나 찾아서 반환.
	@RequestMapping("/topics/{id}")
	public String getTopic(@PathVariable String id) {
		return "Hello wolrd";
	}
	
	// 토픽 추가.
	@RequestMapping(method=RequestMethod.POST, value="/topics")
	public String addTopic() {
		return "Hello world";
	}
	
	// Id로 토픽을 하나 찾고, 해당 토픽을 업데이트.
	@RequestMapping(method=RequestMethod.PUT, value="/topics/{id}")
	public String updateTopic() {
		return "Hello world";
	}
	
	// Id로 토픽을 하나 찾고, 해당 토픽을 삭제함.
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{id}")
	public String deleteTopic() {
		return "Hello world";
	}
}
