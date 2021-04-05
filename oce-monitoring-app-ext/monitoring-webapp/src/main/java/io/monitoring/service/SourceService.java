/**
 * 
 */
package io.monitoring.service;

import io.monitoring.domain.Source;

import java.util.List;
import java.util.Optional;

/**
 * @author vipink
 *
 */
public interface SourceService {

	Source save(Source source);

	List<Source> findAll();
	
	Optional<Source> findById(Long id);
	
	void delete(Long id);
	
	Source update(Source source);

}
