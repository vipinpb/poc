/**
 * 
 */
package io.monitoring.repository;

import io.monitoring.domain.Source;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author vipink
 *
 */
@Repository
public interface SourceRepository extends CrudRepository<Source, Long> {
	Optional<Source> findByName(String name);
}
