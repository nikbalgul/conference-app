package com.nikbal.conference.app.model;

import java.util.List;

import lombok.Data;

@Data
public class Track {
	private List<Conference> conferenceList;
}
