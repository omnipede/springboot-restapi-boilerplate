package io.omnipede.springbootrestapiboilerplate.topic.domain.usecase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.omnipede.springbootrestapiboilerplate.topic.domain.entity.Topic;
import io.omnipede.springbootrestapiboilerplate.topic.domain.entity.TopicRepository;

/**
 * Topic 관련 REST API 에 대한 비즈니스 로직을 구현하는 서비스.
 */
@Service
public class TopicService {

	@Autowired
	private TopicRepository topicRepository;
	
	// 모든 토픽 반환.
	public List<Topic> getAllTopics() {
		List<Topic> topics = new ArrayList<>();
		topicRepository.findAll().forEach(topics::add);
		return topics;
	}
	
	// id와 일치하는 토픽을 하나 찾아서 반환.
	public Topic getTopics(String id) {
		Topic topic = topicRepository.findById(id).get();
		return topic;
	}
	
	// Topic을 repository에 저장함.
	public void addTopic(Topic topic) {
		topicRepository.save(topic);
	}
	
	// Topic 내용을 업데이트함.
	public void updateTopic(Topic topic) {
		topicRepository.save(topic);
	}
	
	// id값을 가지는 topic을 전부 삭제함.
	public void deleteTopic(String id) {
		topicRepository.deleteById(id);
	}
}
