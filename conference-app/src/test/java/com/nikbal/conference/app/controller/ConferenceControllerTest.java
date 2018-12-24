package com.nikbal.conference.app.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.nikbal.conference.app.model.Conference;
import com.nikbal.conference.app.service.ConferenceService;

public class ConferenceControllerTest extends AbstractControllerTest {

	@Mock
	private ConferenceService conferenceService;

	@InjectMocks
	private ConferenceController conferenceController;

	@Captor
	private ArgumentCaptor<Conference> conferenceCaptor;

	private List<Conference> conferenceList;

	@Autowired
	MockMvc mockMvc;

	@Before
	public void setUp() {
		conferenceList = new ArrayList<>();
		conferenceList.add(new Conference("Architecting Your Codebase", "Akın Kaldıroğlu", 60));
		conferenceList.add(new Conference("Overdoing it in Python", "Mustafa Ulu", 30));
		conferenceList.add(new Conference("Flavors of Concurrency", "Necmeddin İkbal GÜL", 45));
		conferenceList.add(new Conference("Ruby Errors from Mismatched Gem Versions", "Halit Can", 60));
		conferenceList.add(new Conference("JUnit 5 - Shaping the Future of Testing on the JVM", "Zafer Cansız", 55));
		when(conferenceService.getConference()).thenReturn(conferenceList);
	}

	@Test
	public void listConferences() throws Exception {
		mockMvc.perform(get("/view")).andExpect(status().isOk());
	}

	@Test
	public void addConference() throws Exception {
		mockMvc.perform(get("/add")).andExpect(status().isOk());
	}
}
