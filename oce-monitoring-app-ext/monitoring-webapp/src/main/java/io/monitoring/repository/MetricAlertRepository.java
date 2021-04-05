/**
 * 
 */
package io.monitoring.repository;

import io.monitoring.domain.MetricAlert;
import io.monitoring.domain.Source;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author vipink
 *
 */
@Repository
public interface MetricAlertRepository extends CrudRepository<MetricAlert, Long> {
	Iterable<MetricAlert> findByRuleId(Long ruleId);
}
