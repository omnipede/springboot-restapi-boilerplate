package io.omnipede.springbootrestapiboilerplate.topic;

import org.springframework.data.repository.CrudRepository;

/**
 * Spring boot jpa repository
 * 특이사항 없음.
 */
public interface TopicRepository extends CrudRepository<Topic, String> {

}
