package com.nikbal.conference.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "conference")
@Data
public class Conference {

	public Conference() {
	}

	public Conference(String topic, String speaker, int duration) {
		this.speaker = speaker;
		this.topic = topic;
		this.duration = duration;
	}

	public Conference(String topic, String speaker, int duration, String time) {
		this.speaker = speaker;
		this.topic = topic;
		this.duration = duration;
		this.time = time;
	}

	@Id
	@GeneratedValue
	private Long id;
	private String speaker;
	private String topic;
	private Integer duration;
	private String time;

}
