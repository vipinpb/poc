/**
 * 
 */
package io.monitoring.service;

import io.monitoring.domain.MetricSchedule;

import java.util.List;
import java.util.Optional;

/**
 * @author vipink
 *
 */
public interface MetricScheduleService {
	MetricSchedule save(MetricSchedule metricSchedule);

	List<MetricSchedule> findAll();
	
	Optional<MetricSchedule> findById(Long id);
	
	void delete(Long id);
	
	MetricSchedule update(MetricSchedule metricSchedule);
	
	Optional<MetricSchedule> startSchedule(Long id) ;
	
	Optional<MetricSchedule> stopSchedule(Long id) ;

}
