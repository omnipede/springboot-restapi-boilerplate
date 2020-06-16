package io.omnipede.springbootrestapiboilerplate.topic;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.omnipede.springbootrestapiboilerplate.response.OkResponse;

import javax.validation.Valid;

@RestController
public class TopicController {

	// TODO: @RequestBody 타입 체크 고안하기
	// TODO: logging 추가
	// TODO: filter 추가
	// TODO: clean architecture 구현하기
	// TODO: response body 리팩토링하기 (필요없는 필드 삭제?)

	@Autowired
	private TopicService topicService;

	@ApiOperation(value="Get all topics", notes="DB에 저장된 모든 토픽을 반환하는 API")
	@RequestMapping(method=RequestMethod.GET, value="/topics", headers="accept=application/json")
	public @ResponseBody OkResponse<List<Topic>> getAllTopics() {
		List<Topic> topicList = topicService.getAllTopics();
		OkResponse<List<Topic>> response = new OkResponse<>(topicList);
		return response;
	}

	@ApiOperation(value="Get a topic", notes="Topic id로 특정 토픽을 하나 찾아 반환하는 API")
	@RequestMapping(method=RequestMethod.GET, value="/topics/{id}", headers="accept=application/json")
	public @ResponseBody OkResponse<Topic> getTopic(@PathVariable String id) {
		Topic topic = topicService.getTopics(id);
		OkResponse<Topic> response = new OkResponse<>(topic);
		return response;
	}

	@ApiOperation(value="Add a topic", notes="새로운 토픽을 추가하는 API")
	@RequestMapping(method=RequestMethod.POST, value="/topics", headers="accept=application/json")
	public @ResponseBody OkResponse<?> addTopic(@RequestBody @Valid TopicDTO dto) {
		topicService.addTopic(new Topic(dto.getId(), dto.getName(), dto.getDescription()));
		OkResponse<?> response = new OkResponse<>();
		return response;
	}

	@ApiOperation(value="Update a topic", notes="토픽을 업데이트하는 API")
	@RequestMapping(method=RequestMethod.PUT, value="/topics", headers="accept=application/json")
	public @ResponseBody OkResponse<?> updateTopic(@RequestBody @Valid TopicDTO dto) {
		topicService.updateTopic(new Topic(dto.getId(), dto.getName(), dto.getDescription()));
		OkResponse<?> response = new OkResponse<>();
		return response;
	}

	@ApiOperation(value="Delete a topic", notes="토픽을 삭제하는 API")
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{id}", headers="accept=application/json")
	public @ResponseBody OkResponse<?> deleteTopic(@PathVariable String id) {
		topicService.deleteTopic(id);
		OkResponse<?> response = new OkResponse<>();
		return response;
	}
}
