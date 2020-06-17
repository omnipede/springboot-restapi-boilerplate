package io.omnipede.springbootrestapiboilerplate.topic;

import java.util.ArrayList;
import java.util.List;

import io.omnipede.springbootrestapiboilerplate.global.response.ApiResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.omnipede.springbootrestapiboilerplate.global.response.ApiResponseWithData;

import javax.validation.Valid;

@RestController
public class TopicController {

	// TODO: @RequestBody 타입 체크 고안하기 <-
	// TODO: logging 추가,
	// TODO: request ~ response filter 추가
	// TODO: clean architecture 구현하기

	@Autowired
	private TopicService topicService;

	@ApiOperation(value="Get all topics", notes="DB에 저장된 모든 토픽을 반환하는 API")
	@RequestMapping(method=RequestMethod.GET, value="/topics", headers="accept=application/json")
	public @ResponseBody
	ApiResponseWithData<List<TopicDTO>> getAllTopics() {
		// 모든 토픽을 찾아서 DTO로 바꿔 반환
		List<Topic> topicList = topicService.getAllTopics();
		List<TopicDTO> topicDTOs = new ArrayList<>();
		topicList.forEach((topic) -> topicDTOs.add(new TopicDTO(topic.getId(), topic.getName(), topic.getDescription())));
		return new ApiResponseWithData<>(topicDTOs);
	}

	@ApiOperation(value="Get a topic", notes="Topic id로 특정 토픽을 하나 찾아 반환하는 API")
	@RequestMapping(method=RequestMethod.GET, value="/topics/{id}", headers="accept=application/json")
	public @ResponseBody
	ApiResponseWithData<TopicDTO> getTopic(
			@ApiParam(value="Topic id", example="Java")
			@PathVariable String id
	) {
		// Topic을 하나 찾아서 반환
		Topic topic = topicService.getTopics(id);
		return new ApiResponseWithData<>(new TopicDTO(topic.getId(), topic.getName(), topic.getDescription()));
	}

	@ApiOperation(value="Add a topic", notes="새로운 토픽을 추가하는 API")
	@RequestMapping(method=RequestMethod.POST, value="/topics", headers="accept=application/json")
	public @ResponseBody
	ApiResponse addTopic(
			@ApiParam(value="Topic request model")
			@RequestBody @Valid TopicDTO dto
	) {
		topicService.addTopic(new Topic(dto.getId(), dto.getName(), dto.getDescription()));
		return new ApiResponse(200);
	}

	@ApiOperation(value="Update a topic", notes="토픽을 업데이트하는 API")
	@RequestMapping(method=RequestMethod.PUT, value="/topics", headers="accept=application/json")
	public @ResponseBody
	ApiResponse updateTopic(
			@ApiParam(value="Topic request model")
			@RequestBody @Valid TopicDTO dto
	) {
		topicService.updateTopic(new Topic(dto.getId(), dto.getName(), dto.getDescription()));
		return new ApiResponse(200);
	}

	@ApiOperation(value="Delete a topic", notes="토픽을 삭제하는 API")
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{id}", headers="accept=application/json")
	public @ResponseBody
	ApiResponse deleteTopic(
			@ApiParam(value="Topic id", example="Java")
			@PathVariable String id
	) {
		topicService.deleteTopic(id);
		return new ApiResponse(200);
	}
}
