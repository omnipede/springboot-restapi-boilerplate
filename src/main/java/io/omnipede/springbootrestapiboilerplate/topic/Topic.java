package io.omnipede.springbootrestapiboilerplate.topic;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Topic (주제)를 나타내는 엔티티.
 * 샘플용 엔티티이고 현재로서는 특이사항 없음.
 * 추후에 다른 엔티티와 연관관계 맵핑을 통해 JPA를 공부해볼 생각임.
 */
@Entity
public class Topic {

	@Id
	private String id;
	private String name;
	private String description;
	
	public Topic() {
		
	}
	
	public Topic(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
