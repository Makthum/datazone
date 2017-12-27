package com.jpr.app.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpr.app.domain.Equipment;
import com.jpr.app.domain.MaintSchedule;
import com.jpr.app.repository.EquipmentRepository;
import com.jpr.app.repository.MaintScehduleRepository;
import com.jpr.app.service.dto.ScheduleDTO;

import net.fortuna.ical4j.filter.Filter;
import net.fortuna.ical4j.filter.PeriodRule;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.PeriodList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.RRule;
import net.fortuna.ical4j.model.property.Version;

@Service
public class ScheduleService {

	@Autowired
	private MaintScehduleRepository scheduleRepo;

	@Autowired
	private EquipmentRepository eRepo;

	private Calendar cal;

	public void setup() throws ParseException {
		cal = createCalendar();
		cal.getComponents().addAll(getEvents(getSchedule()));
	}

	private Calendar createCalendar() {
		Calendar calendar = new Calendar();
		calendar.getProperties().add(new ProdId("JPR Maintenance"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		return calendar;
	}

	private List<VEvent> getEvents(List<MaintSchedule> schedule) throws ParseException {
		List<VEvent> events = new ArrayList<>();
		for (MaintSchedule s : schedule) {
			VEvent event = new VEvent(new Date(s.getStartDate()), s.getId().toString());
			event.getProperties().add(getRule(s.getFrequency(), new Date(s.getEndDate())));
			event.getProperties().add(new Duration(new Dur(0, 1, 0, 0)));
			events.add(event);
		}
		return events;
	}

	private List<MaintSchedule> getSchedule() {
		return scheduleRepo.findAll();
	}

	private RRule getRule(int frequency, Date endate) throws ParseException {
		String rrule = "FREQ=DAILY;INTERVAL=" + String.valueOf(frequency) + ";UNTIL=" + endate.toString();
		return new RRule(rrule);
	}

	public Calendar getInstance() throws ParseException {

		setup();
		return cal;
	}

	public List<VEvent> getEvents(Date date) {
		Period period = new Period(new DateTime(date), new Dur(30, 0, 0, 0));
		Filter filter = new Filter(new PeriodRule(period));
		Collection<VEvent> eventsToday = filter.filter(cal.getComponents(Component.VEVENT));
		return new ArrayList<>(eventsToday);
	}

	public Date getNextEventDate(Integer id, Integer frequency) {
		VEvent e = null;
		for (Object o : cal.getComponents("VEVENT")) {
			Component c = (Component) o;
			if (c.getProperty("SUMMARY").getValue().equalsIgnoreCase(id.toString()))
				e = (VEvent) c;
		}
		Period period = new Period(new DateTime(), new Dur(frequency, 0, 0, 0));
		PeriodList p = e.calculateRecurrenceSet(period);
		Iterator<Period> it = p.iterator();
		Period result = null;
		while (it.hasNext()) {
			result = it.next();
			break;
		}
		if (result == null)
			return null;
		return result.getStart();
	}

	public void updateNextDate() {
		List<MaintSchedule> schedule = scheduleRepo.findAll();
		for (MaintSchedule s : schedule) {
			Date nxt = getNextEventDate(s.getId(), s.getFrequency());
			if (s.getNextService() == null || nxt.after(s.getNextService())) {
				s.setNextService(nxt);
				s.setTaskCreated("n");
			}
			scheduleRepo.save(s);
		}
	}

	public MaintSchedule createSchedule(ScheduleDTO s) {
		MaintSchedule temp = null;
		if (s.getId() != null) {
			temp = scheduleRepo.findOne(s.getId());
		}
		Equipment e = eRepo.findOne(s.getEquipmentId());
		if (temp == null) {
			temp = new MaintSchedule();
		}
		temp.setEndDate(s.getEndDate());
		temp.setEquipment(e);
		temp.setFrequency(s.getFrequency());
		temp.setLastService(s.getLastService());
		temp.setNextService(s.getNextService());
		temp.setNoOfPersonnel(s.getNoOfPersonnel());
		temp.setSpareRequired(s.getSpareRequired());
		temp.setStartDate(s.getStartDate());
		temp.setTaskCreated("n");
		temp.setTeam(s.getTeam());
		temp.setToolsRequired(s.getToolsRequired());
		temp.setWorkType(s.getWorkType());
		return scheduleRepo.save(temp);
	}

	public List<MaintSchedule> getAll() {
		return scheduleRepo.findAll();
	}
}
