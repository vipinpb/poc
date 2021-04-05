/**
 * 
 */
package io.monitoring.repository;

import io.monitoring.domain.MetricSchedule;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author vipink
 *
 */
@Repository
public interface MetricScheduleRepository extends CrudRepository<MetricSchedule, Long> {

}
