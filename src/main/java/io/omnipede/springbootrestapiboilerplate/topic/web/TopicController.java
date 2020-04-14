package io.omnipede.springbootrestapiboilerplate.topic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.omnipede.springbootrestapiboilerplate.topic.domain.entity.Topic;
import io.omnipede.springbootrestapiboilerplate.topic.domain.usecase.TopicService;

@RestController
public class TopicController {

	// TODO: Swagger를 이용한 API 문서 생성.
	
	@Autowired
	private TopicService topicService;
	
	// 모든 토픽 반환.
	@RequestMapping("/topics")
	public List<Topic> getAllTopics() {
		return topicService.getAllTopics();
	}
	
	// Id로 토픽을 하나 찾아서 반환.
	@RequestMapping("/topics/{id}")
	public Topic getTopic(@PathVariable String id) {
		return topicService.getTopics(id);
	}
	
	// 토픽 추가.
	@RequestMapping(method=RequestMethod.POST, value="/topics")
	public void addTopic(@RequestBody Topic topic) {
		topicService.addTopic(topic);
	}
	
	// Id로 토픽을 하나 찾고, 해당 토픽을 업데이트.
	@RequestMapping(method=RequestMethod.PUT, value="/topics/{id}")
	public void updateTopic(@RequestBody Topic topic) {
		topicService.updateTopic(topic);
	}
	
	// Id로 토픽을 하나 찾고, 해당 토픽을 삭제함.
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{id}")
	public void deleteTopic(@PathVariable String id) {
		topicService.deleteTopic(id);
	}
}
