package io.omnipede.springbootrestapiboilerplate.domain.topic;

import org.springframework.data.repository.CrudRepository;

/**
 * Spring boot jpa repository
 * 특이사항 없음.
 */
public interface TopicRepository extends CrudRepository<Topic, String> {

}
