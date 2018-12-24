package com.nikbal.conference.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikbal.conference.app.model.Conference;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {

}
