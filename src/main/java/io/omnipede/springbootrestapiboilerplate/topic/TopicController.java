package io.omnipede.springbootrestapiboilerplate.topic;

import java.util.ArrayList;
import java.util.List;
import io.omnipede.springbootrestapiboilerplate.topic.response.JsonResponse;
import io.omnipede.springbootrestapiboilerplate.topic.response.JsonResponseWithData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TopicController {

	@Autowired
	private TopicService topicService;

	@RequestMapping(method=RequestMethod.GET, value="/topics", headers="accept=application/json")
	public @ResponseBody
	JsonResponseWithData<List<TopicDTO>> getAllTopics() {
		// 모든 토픽을 찾아서 DTO 로 바꿔 반환
		List<Topic> topicList = topicService.getAllTopics();
		List<TopicDTO> topicDTOs = new ArrayList<>();
		topicList.forEach((topic) -> topicDTOs.add(new TopicDTO(topic.getId(), topic.getName(), topic.getDescription())));
		return new JsonResponseWithData<>(topicDTOs);
	}

	@RequestMapping(method=RequestMethod.GET, value="/topics/{id}", headers="accept=application/json")
	public @ResponseBody
	JsonResponseWithData<TopicDTO> getTopic(@PathVariable String id) {
		// Topic 을 하나 찾아서 반환
		Topic topic = topicService.getTopics(id);
		return new JsonResponseWithData<>(new TopicDTO(topic.getId(), topic.getName(), topic.getDescription()));
	}

	@RequestMapping(method=RequestMethod.POST, value="/topics", headers="accept=application/json")
	public @ResponseBody
	JsonResponse addTopic(@RequestBody @Valid TopicDTO dto) {
		topicService.addTopic(new Topic(dto.getId(), dto.getName(), dto.getDescription()));
		return new JsonResponse();
	}

	@RequestMapping(method=RequestMethod.PUT, value="/topics", headers="accept=application/json")
	public @ResponseBody
	JsonResponse updateTopic(@RequestBody @Valid TopicDTO dto) {
		topicService.updateTopic(new Topic(dto.getId(), dto.getName(), dto.getDescription()));
		return new JsonResponse();
	}

	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{id}", headers="accept=application/json")
	public @ResponseBody
	JsonResponse deleteTopic(@PathVariable String id) {
		topicService.deleteTopic(id);
		return new JsonResponse();
	}
}
