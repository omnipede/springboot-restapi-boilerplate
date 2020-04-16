package io.omnipede.springbootrestapiboilerplate.topic.domain.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.omnipede.springbootrestapiboilerplate.common.exception.BusinessException;
import io.omnipede.springbootrestapiboilerplate.common.model.ErrorCode;
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
		// topic이 하나도 없으면 예외처리.
		if (topics.size() <= 0) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_EXISTS);
		}
		// topic list 반환.
		return topics;
	}
	
	// id와 일치하는 토픽을 하나 찾아서 반환.
	public Topic getTopics(String id) {
		Optional<Topic> optionalWrappedTopic = topicRepository.findById(id);
		// topic 이 존재하지 않으면 예외처리.
		if (optionalWrappedTopic.isEmpty()){
			throw new BusinessException(ErrorCode.RESOURCE_NOT_EXISTS);
		}
		// topic 반환.
		Topic topic = optionalWrappedTopic.get();
		return topic;
	}
	
	// Topic을 repository에 저장함.
	public void addTopic(Topic topic) {
		topicRepository.save(topic);
	}
	
	// Topic 내용을 업데이트함.
	public void updateTopic(Topic topic) {
		Optional<Topic> optionalWrappedTopic = topicRepository.findById(topic.getId());
		// 업데이트할 topic이 존재하지 않으면 예외처리.
		if (optionalWrappedTopic.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_EXISTS);
		}
		// topic 업데이트.
		topicRepository.save(topic);
	}
	
	// id값을 가지는 topic을 전부 삭제함.
	public void deleteTopic(String id) {
		Optional<Topic> optionalWrappedTopic = topicRepository.findById(id);
		// 삭제할 topic이 존재하지 않으면 예외처리.
		if (optionalWrappedTopic.isEmpty()) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_EXISTS);
		}
		// topic 삭제.
		topicRepository.deleteById(id);
	}
}
