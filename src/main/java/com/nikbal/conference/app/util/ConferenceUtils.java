package com.nikbal.conference.app.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nikbal.conference.app.model.Conference;
import com.nikbal.conference.app.model.Track;

public class ConferenceUtils {

	private ConferenceUtils() {
	}

	public static List<Track> createAgenda(List<Conference> conferenceList) {
		Integer sumConferenceDuration = sumDuration(conferenceList);
		Integer capacityTrack = 420;
		Integer countTrack = sumConferenceDuration / capacityTrack + 1;
		List<Track> trackList = new ArrayList<>();
		for (int i = 0; i < countTrack; i++) {
			Integer startMinutes = 9 * 60;
			Integer endMinutes = 12 * 60;
			Integer afternoonStartMinutes = 13 * 60;
			Integer afternoonEndMinutes = 17 * 60;
			Collections.shuffle(conferenceList);
			Track track = new Track();
			Conference lunchConference = new Conference("Lunch", "", 60, "12:00PM");
			Conference networkingConference = new Conference("Networking Event", "", 0, "05:00PM");
			List<Conference> conferenceListTrackMorning = createConferenceList(conferenceList, startMinutes, endMinutes,
					"09:00AM", lunchConference);
			conferenceList.removeAll(conferenceListTrackMorning);
			List<Conference> conferenceListTrackAfternoon = createConferenceList(conferenceList, afternoonStartMinutes,
					afternoonEndMinutes, "01:00PM", networkingConference);
			List<Conference> mergedConferenceList = mergedConferenceList(conferenceListTrackMorning,
					conferenceListTrackAfternoon);
			track.setConferenceList(mergedConferenceList);
			trackList.add(track);
			conferenceList.removeAll(mergedConferenceList);
		}
		return trackList;
	}

	private static List<Conference> mergedConferenceList(List<Conference> conferenceListTrackMorning,
			List<Conference> conferenceListTrackAfternoon) {
		List<Conference> mergedConferenceList = new ArrayList<>();
		mergedConferenceList.addAll(conferenceListTrackMorning);
		mergedConferenceList.addAll(conferenceListTrackAfternoon);
		return mergedConferenceList;
	}

	private static List<Conference> createConferenceList(List<Conference> conferenceList, Integer startMinutes,
			Integer endMinutes, String firstConferenceTime, Conference endConference) {
		List<Conference> conferenceListTrack = new ArrayList<>();
		List<Conference> tempConferenceList = new ArrayList<>();
		tempConferenceList.addAll(conferenceList);
		addConfereceToTrack(conferenceList, startMinutes, endMinutes, firstConferenceTime, conferenceListTrack,
				tempConferenceList);
		addTrackConferenceList(conferenceListTrack, endConference);
		return conferenceListTrack;
	}

	private static void addConfereceToTrack(List<Conference> conferenceList, Integer startMinutes, Integer endMinutes,
			String firstConferenceTime, List<Conference> conferenceListTrack, List<Conference> tempConferenceList) {
		for (Conference conference : conferenceList) {
			if (conference == conferenceList.get(0)) {
				conference.setTime(firstConferenceTime);
				addTrackConferenceList(conferenceListTrack, conference);
				startMinutes += conference.getDuration();
			} else {
				startMinutes += conference.getDuration();
				if (startMinutes == endMinutes) {
					findConferenceEquality(startMinutes, conferenceListTrack, conference);
					return;
				}
				if (startMinutes < endMinutes) {
					findOptimalConferenceDeficiency(startMinutes, endMinutes, conferenceListTrack, tempConferenceList,
							conference);
				}
				if (startMinutes > endMinutes) {
					findOptimalConferenceOverflow(startMinutes, endMinutes, conferenceListTrack, tempConferenceList,
							conference);
					return;
				}
			}
		}
	}

	private static void findConferenceEquality(Integer startMinutes, List<Conference> conferenceListTrack,
			Conference conference) {
		conference.setTime(convertMinutesToTimeFormat(startMinutes - conference.getDuration()));
		addTrackConferenceList(conferenceListTrack, conference);
	}

	private static void findOptimalConferenceDeficiency(Integer startMinutes, Integer endMinutes,
			List<Conference> conferenceListTrack, List<Conference> tempConferenceList, Conference conference) {
		startMinutes -= conference.getDuration();
		Conference optimalConference = null;
		Integer remainingTime = endMinutes - startMinutes;
		tempConferenceList.removeAll(conferenceListTrack);
		for (Conference tempConference : tempConferenceList) {
			if (remainingTime == tempConference.getDuration()) {
				optimalConference = tempConference;
			}
		}
		if (optimalConference != null) {
			startMinutes += conference.getDuration();
			optimalConference.setTime(convertMinutesToTimeFormat(startMinutes - conference.getDuration()));
			addTrackConferenceList(conferenceListTrack, optimalConference);
		} else {
			startMinutes += conference.getDuration();
			conference.setTime(convertMinutesToTimeFormat(startMinutes - conference.getDuration()));
			addTrackConferenceList(conferenceListTrack, conference);
		}
	}

	private static void findOptimalConferenceOverflow(Integer startMinutes, Integer endMinutes,
			List<Conference> conferenceListTrack, List<Conference> tempConferenceList, Conference conference) {
		startMinutes -= conference.getDuration();
		Integer remainingTime = endMinutes - startMinutes;
		tempConferenceList.removeAll(conferenceListTrack);
		Conference optimalConference = getOptimalConference(tempConferenceList, remainingTime);
		if (optimalConference != null) {
			startMinutes += optimalConference.getDuration();
			optimalConference.setTime(convertMinutesToTimeFormat(startMinutes - optimalConference.getDuration()));
			addTrackConferenceList(conferenceListTrack, optimalConference);
		}
	}

	private static Conference getOptimalConference(List<Conference> tempConferenceList, Integer remainingTime) {
		Integer minimumRemainingTime = 420;
		Conference optimalConference = null;
		for (Conference conference : tempConferenceList) {
			if (remainingTime == conference.getDuration()) {
				return conference;
			}
		}
		for (Conference conference : tempConferenceList) {
			if (remainingTime > conference.getDuration()
					&& minimumRemainingTime > remainingTime - conference.getDuration()) {
				minimumRemainingTime = remainingTime - conference.getDuration();
				optimalConference = conference;
			}
		}
		return optimalConference;
	}

	private static void addTrackConferenceList(List<Conference> conferenceListTrack, Conference conference) {
		if (!conferenceListTrack.contains(conference)) {
			conferenceListTrack.add(conference);
		}
	}

	private static Integer sumDuration(List<Conference> conferenceList) {
		Integer sum = 0;
		for (Conference conference : conferenceList) {
			sum += conference.getDuration();
		}
		return sum;
	}

	private static String convertMinutesToTimeFormat(Integer minutes) {
		String minute = String.valueOf(minutes % 60);
		String minuteStr = minutes % 60 < 10 ? "0" + minute : minute;
		Integer hour = minutes / 60;
		String hourStr = minutes / 60 < 10 ? "0" + hour : "" + hour;
		Integer hour2 = (minutes / 60) % 12;
		return hour > 12 ? "0" + hour2 + ":" + minuteStr + "PM" : hourStr + ":" + minuteStr + "AM";
	}
}
