package com.nikbal.conference.app.data.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nikbal.conference.app.model.Conference;
import com.nikbal.conference.app.repository.ConferenceRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ConferenceServiceTest {

	@Autowired
	protected ConferenceRepository conferenceRepository;

	@Test
	public void listInitiallyEmpty() {
		List<Conference> list = conferenceRepository.findAll();
		assertEquals(0, list.size());
	}

	@Test
	public void addConferenceRecord() {
		List<Conference> allConferences = conferenceRepository.findAll();
		int found = allConferences.size();
		conferenceRepository.save(new Conference("A World Without HackerNews", "Sercan Akçalı", 60));
		List<Conference> allConferences2 = conferenceRepository.findAll();
		assertEquals(found + 1, allConferences2.size());
		assertNotNull(allConferences2.get(found).getId());
		assertEquals("Sercan Akçalı", allConferences2.get(found).getSpeaker());
		assertEquals("A World Without HackerNews", allConferences2.get(found).getTopic());
	}

	@Test
	public void findConferenceRecordById() {
		conferenceRepository.save(new Conference("A World Without HackerNews", "Sercan Akçalı", 60));
		conferenceRepository.save(new Conference("A World Without HackerNews2", "Sercan Akçalı", 60));
		conferenceRepository.save(new Conference("A World Without HackerNews3", "Sercan Akçalı", 60));
		conferenceRepository.save(new Conference("A World Without HackerNews4", "Sercan Akçalı", 60));

		List<Conference> allConferences = conferenceRepository.findAll();

		assertEquals(4, allConferences.size());

		Conference foundConference = allConferences.get(0);
		assertEquals("Sercan Akçalı", foundConference.getSpeaker());
		assertEquals("A World Without HackerNews", foundConference.getTopic());
	}

	@Test
	public void findConferenceRecordByIdWhenNotFound() {
		conferenceRepository.save(new Conference("A World Without HackerNews", "Sercan Akçalı", 60));
		conferenceRepository.save(new Conference("A World Without HackerNews2", "Sercan Akçalı", 60));
		conferenceRepository.save(new Conference("A World Without HackerNews3", "Sercan Akçalı", 60));
		conferenceRepository.save(new Conference("A World Without HackerNews4", "Sercan Akçalı", 60));

		List<Conference> allConferences = conferenceRepository.findAll();

		assertEquals(4, allConferences.size());

		Conference foundConference = null;
		for (Conference conference : allConferences) {
			if (conference.getTopic().equals("A World Without HackerNews5")) {
				foundConference = conference;
			}
		}

		assertNull(foundConference);
	}

	@Test
	public void findAllConferences() {
		conferenceRepository.save(new Conference("A World Without HackerNews", "Sercan Akçalı", 60));
		conferenceRepository.save(new Conference("A World Without HackerNews2", "Sercan Akçalı", 60));
		conferenceRepository.save(new Conference("A World Without HackerNews3", "Sercan Akçalı", 60));
		conferenceRepository.save(new Conference("A World Without HackerNews4", "Sercan Akçalı", 60));
		List<Conference> allConferences = conferenceRepository.findAll();
		assertEquals(4, allConferences.size());
	}
}
