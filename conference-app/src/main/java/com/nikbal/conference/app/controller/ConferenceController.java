package com.nikbal.conference.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.nikbal.conference.app.model.Conference;
import com.nikbal.conference.app.model.ConferenceDTO;
import com.nikbal.conference.app.model.Track;
import com.nikbal.conference.app.service.ConferenceService;
import com.nikbal.conference.app.util.ConferenceUtils;

@Controller
public class ConferenceController implements WebMvcConfigurer {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/add").setViewName("add");
	}

	@Autowired
	ConferenceService conferenceService;

	@GetMapping("/")
	public String home1() {
		return "/home";
	}

	@GetMapping("/home")
	public String home() {
		return "/home";
	}

	@GetMapping("/about")
	public String about() {
		return "/about";
	}

	@GetMapping("/add")
	public String add(ConferenceDTO conferenceDTO) {
		return "/add";
	}

	@GetMapping("/view")
	public String view(Model model) {
		List<Conference> conferenceList = conferenceService.getConference();
		List<Track> trackList = ConferenceUtils.createAgenda(conferenceList);
		model.addAttribute("trackList", trackList);
		return "/view";
	}

	@PostMapping("/add")
	public String addConference(@Valid ConferenceDTO conferenceDTO, BindingResult result) {
		if (result.hasErrors()) {  
			return "/add";
		}
		Conference conference = convertConference(conferenceDTO);
		conferenceService.addConference(conference);
		return "redirect:add";
	}

	private Conference convertConference(@Valid ConferenceDTO conferenceDTO) {
		return new Conference(conferenceDTO.getTopic(), conferenceDTO.getSpeaker(), conferenceDTO.getDuration());
	}
}
