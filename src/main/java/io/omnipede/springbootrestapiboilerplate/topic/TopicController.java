package io.omnipede.springbootrestapiboilerplate.topic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.omnipede.springbootrestapiboilerplate.response.OkResponse;

import javax.validation.Valid;

@RestController
public class TopicController {

	// TODO: Swagger를 이용한 API 문서 생성
	// TODO: @ResponseBody annotation 사용해보기
	// TODO: @RequestBody 타입 체크 고안하기
	
	@Autowired
	private TopicService topicService;
	
	// 모든 토픽 반환.
	@RequestMapping(method=RequestMethod.GET, value="/topics", headers="accept=application/json")
	public ResponseEntity<OkResponse<List<Topic>>> getAllTopics() {
		List<Topic> topicList = topicService.getAllTopics();
		OkResponse<List<Topic>> response = new OkResponse<>(topicList);
		return ResponseEntity.ok(response);
	}
	
	// Id로 토픽을 하나 찾아서 반환.
	@RequestMapping(method=RequestMethod.GET, value="/topics/{id}", headers="accept=application/json")
	public ResponseEntity<OkResponse<Topic>> getTopic(@PathVariable String id) {
		Topic topic = topicService.getTopics(id);
		OkResponse<Topic> response = new OkResponse<>(topic);
		return ResponseEntity.ok(response);
	}
	
	// 토픽 추가.
	@RequestMapping(method=RequestMethod.POST, value="/topics", headers="accept=application/json")
	public ResponseEntity<OkResponse<?>> addTopic(@RequestBody @Valid TopicDTO dto) {
		topicService.addTopic(new Topic(dto.getId(), dto.getName(), dto.getDescription()));
		OkResponse<?> response = new OkResponse<>();
		return ResponseEntity.ok(response);
	}
	
	// 토픽을 하나 찾고, 해당 토픽을 업데이트.
	@RequestMapping(method=RequestMethod.PUT, value="/topics", headers="accept=application/json")
	public ResponseEntity<OkResponse<?>> updateTopic(@RequestBody @Valid TopicDTO dto) {
		topicService.updateTopic(new Topic(dto.getId(), dto.getName(), dto.getDescription()));
		OkResponse<?> response = new OkResponse<>();
		return ResponseEntity.ok(response);
	}
	
	// Id로 토픽을 하나 찾고, 해당 토픽을 삭제함.
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{id}", headers="accept=application/json")
	public ResponseEntity<OkResponse<?>> deleteTopic(@PathVariable String id) {
		topicService.deleteTopic(id);
		OkResponse<?> response = new OkResponse<>();
		return ResponseEntity.ok(response);
	}
}
