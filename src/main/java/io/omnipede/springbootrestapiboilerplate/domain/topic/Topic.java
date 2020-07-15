package io.omnipede.springbootrestapiboilerplate.domain.topic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Topic (주제)를 나타내는 엔티티.
 * 샘플용 엔티티이고 현재로서는 특이사항 없음.
 * 추후에 다른 엔티티와 연관관계 맵핑을 통해 JPA 를 공부해볼 생각임.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Topic {

	@Id
	private String id;
	private String name;
	private String description;
	
	public Topic(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
}
