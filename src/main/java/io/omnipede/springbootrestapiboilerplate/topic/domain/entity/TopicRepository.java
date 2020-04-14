package io.omnipede.springbootrestapiboilerplate.topic.domain.entity;

import org.springframework.data.repository.CrudRepository;

/**
 * Spring boot jpa repository
 * 특이사항 없음.
 */
public interface TopicRepository extends CrudRepository<Topic, String> {

}
