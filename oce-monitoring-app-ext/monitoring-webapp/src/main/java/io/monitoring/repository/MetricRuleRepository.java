/**
 * 
 */
package io.monitoring.repository;

import io.monitoring.domain.MetricRule;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author vipink
 *
 */
@Repository
public interface MetricRuleRepository extends CrudRepository<MetricRule, Long> {

	Iterable<MetricRule> findByName(String ruleName);
	
}
