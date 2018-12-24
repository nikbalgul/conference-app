package com.nikbal.conference.app.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ConferenceDTO {

	@NotNull
	@Size(max = 50)
	private String speaker;
	@NotNull
	@Size(max = 100)
	private String topic;
	@NotNull
	@Max(90)
	@Min(5)
	private Integer duration;
	private String time;

}
