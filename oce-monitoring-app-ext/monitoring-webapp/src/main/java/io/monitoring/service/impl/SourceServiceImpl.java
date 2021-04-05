/**
 * 
 */
package io.monitoring.service.impl;

import io.monitoring.domain.Source;
import io.monitoring.repository.SourceRepository;
import io.monitoring.service.SourceService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author vipink
 *
 */
public class SourceServiceImpl implements SourceService {

	@Autowired
	private SourceRepository sourceRepository;
	/**
	 * 
	 */
	public SourceServiceImpl() {}

	@Override
	public Source save(Source source) {
		return sourceRepository.save(source);
	}
	
	@Override
	public Source update(Source source) {
		return sourceRepository.save(source);
	}

	@Override
	public List<Source> findAll() {
		return (List<Source>) sourceRepository.findAll();
	}
	
	public void delete(Long id) {
		sourceRepository.deleteById(id);
	}

	@Override
	public Optional<Source> findById(Long id) {
		return sourceRepository.findById(id);
	}

}
