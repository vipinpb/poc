package io.monitoring.controller;

import io.monitoring.domain.MetricSchedule;
import io.monitoring.manager.MetricsManager;
import io.monitoring.metrics.Status;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")

public class ScheduleController {

	@Autowired
	private MetricsManager metricsManager;

	/**
		 * 
		 */
	public ScheduleController() {}

	@PostMapping("/schedular")
	public MetricSchedule addSource(@RequestBody MetricSchedule metricSchedule) {
		return metricsManager.getMetricScheduleService().save(metricSchedule);
	}

	@GetMapping("/schedular")
	public List<MetricSchedule> getMetricSchedules() {
		return (List<MetricSchedule>) metricsManager.getMetricScheduleService().findAll();
	}

	@GetMapping("/schedular/{id}")
	public ResponseEntity<?>  getMetricSchedule(@PathVariable Long id) {
		Optional<MetricSchedule> scheduleOptonal = metricsManager.getMetricScheduleService().startSchedule(id);
		if (scheduleOptonal.isPresent()) {
			return new ResponseEntity<>(scheduleOptonal.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		}
	}
	
	@PostMapping("/schedular/start")
	public Status startSchedule(@RequestParam Long id) {
		Status status = null;
		Optional<MetricSchedule> scheduleOptonal = metricsManager.getMetricScheduleService().startSchedule(id);
		if (scheduleOptonal.isPresent()) {
			MetricSchedule schedule = scheduleOptonal.get();
			status = new Status( schedule.getSchedule()+" schedular is started");
		} else {
			status = new Status(" No schedular is found");
		}
		return status;
	}
	
	@PostMapping("/schedular/stop")
	public Status stopSchedule(@RequestParam Long id) {
		Status status = null;
		Optional<MetricSchedule> scheduleOptonal = metricsManager.getMetricScheduleService().stopSchedule(id);
		if (scheduleOptonal.isPresent()) {
			MetricSchedule schedule = scheduleOptonal.get();
			status = new Status(schedule.getSchedule() +" Schedule is stopped");
		} else {
			status = new Status(" No schedule is found");
		}
		return status;
	}

	@PutMapping("/schedular/{id}")
	public ResponseEntity<?> update(@RequestBody MetricSchedule source,
			@PathVariable Long id) {
		try {
			Optional<MetricSchedule> existingSource = metricsManager.getMetricScheduleService().findById(id);
			if (existingSource.isPresent()) {
				metricsManager.getMetricScheduleService().update(existingSource.get());
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(existingSource, HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/schedular/{id}")
	public void delete(@PathVariable Long id) {
		metricsManager.getMetricScheduleService().delete(id);
	}
	
    @GetMapping("/schedular/startAllServices")
	public ResponseEntity<?> startAllServices() {
    	try {
    		//Loop over the active shedular.. and start those
    		List<MetricSchedule> schedules = metricsManager.getMetricScheduleService().findAll();
    		for (MetricSchedule schedule : schedules) {
    			Optional<MetricSchedule> scheduleOptonal = metricsManager.getMetricScheduleService().startSchedule(schedule.getId());
    		}
			return new ResponseEntity<>(new Status(" Started rule engine and schedulars"), HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
