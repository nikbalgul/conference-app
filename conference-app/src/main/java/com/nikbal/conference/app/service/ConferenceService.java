package com.nikbal.conference.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nikbal.conference.app.model.Conference;
import com.nikbal.conference.app.repository.ConferenceRepository;

@Service
public class ConferenceService {
	@Autowired
	ConferenceRepository conferenceRepository;

	public void addConference(Conference conference) {
		conferenceRepository.save(conference);
	}

	public List<Conference> getConference() {
		return conferenceRepository.findAll();
	}
}
