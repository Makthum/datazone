package com.jpr.app.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpr.app.domain.Equipment;
import com.jpr.app.domain.MaintSchedule;
import com.jpr.app.domain.Personnel;
import com.jpr.app.domain.Task;
import com.jpr.app.repository.EquipmentRepository;
import com.jpr.app.repository.MaintScehduleRepository;
import com.jpr.app.repository.PersonnelRepository;
import com.jpr.app.repository.TaskRepository;
import com.jpr.app.service.dto.TaskDTO;
import com.jpr.app.service.util.Status;

@Service
public class TaskService {

	@Autowired
	TaskRepository taskRepo;

	@Autowired
	MaintScehduleRepository sRepo;

	@Autowired
	EquipmentRepository eRepo;

	@Autowired
	ScheduleService schService;

	@Autowired
	PersonnelRepository pRepo;

	public void createBatchTask() throws ParseException {
		schService.getInstance();
		schService.updateNextDate();
		List<MaintSchedule> schedule = sRepo.findByTaskCreated("n");
		for (MaintSchedule s : schedule) {
			taskRepo.save(toTask(s));
			s.setTaskCreated("y");
			sRepo.save(s);
			sRepo.flush();
		}

	}

	private Task toTask(MaintSchedule schedule) {
		Task t = new Task();
		t.setDescription("MAINTENANCE -" + schedule.getEquipment().getName());
		t.setEquipment(schedule.getEquipment());
		t.setScheulde(schedule);
		t.setStartDate(schedule.getNextService());
		t.setTaskType("maintenance");
		t.setStatus(Status.OPEN.toString());
		return t;
	}

	public Task createTask(TaskDTO details) {

		Task temp = null;
		if (details.getId() != null)
			temp = taskRepo.findOne(details.getId());
		Equipment e = eRepo.findOne(details.getEquipmentId());
		MaintSchedule s = sRepo.findOne(details.getScheduleId());
		if (temp == null) {
			temp = new Task();
		}
		temp.setDescription(details.getDescription());
		temp.setEndDate(details.getEndDate());
		temp.setEquipment(e);
		temp.setPersonnels(getPersonnels(details.getPersonnelIds()));
		temp.setRemarks(details.getRemarks());

		temp.setSpareUsed(details.getSpareUsed());
		temp.setStartDate(details.getStartDate());
		temp.setTaskType(details.getTaskType());
		temp.setStatus(Status.valueOf(details.getStatus()).toString());
		if (Status.valueOf(details.getStatus()) == Status.CLOSED) {
			s.setLastService(new Date());
			sRepo.save(s);
		}
		temp.setScheulde(s);
		return taskRepo.save(temp);
	}

	public List<Personnel> getPersonnels(List<Integer> ids) {
		List<Personnel> p = new ArrayList<>();
		for (Integer id : ids) {
			p.add(pRepo.findOne(id));
		}
		return p;
	}

	public List<Task> getAllOpened() {
		return taskRepo.findByStatus(Status.OPEN.toString());
	}

	public Task getTask(Integer id) {
		return taskRepo.findOne(id);
	}

	public List<Task> getReport(Date fromDate, Date toDate) {
		return taskRepo.findByStartDateBetween(fromDate, toDate);
	}

}
