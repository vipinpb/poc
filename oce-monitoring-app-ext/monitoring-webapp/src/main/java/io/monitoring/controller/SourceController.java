/**
 * 
 */
package io.monitoring.controller;

import io.monitoring.domain.Source;
import io.monitoring.manager.MetricsManager;
import io.monitoring.service.SourceService;

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
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vipink
 *
 */
@RestController
@RequestMapping("/api/v1")
public class SourceController {

	@Autowired
	private MetricsManager metricsManager;
	/**
	 * 
	 */
	public SourceController() {}

    @PostMapping("/sources")
    public Source addSource(@RequestBody Source source) {
        return metricsManager.getSourceService().save(source);
    }
    
    @GetMapping("/sources")
    public List<Source> getSources() {
        return (List<Source>) metricsManager.getSourceService().findAll();
    }
    
    @GetMapping("/sources/{id}")
    public Source getSources(@PathVariable Long id) {
        return metricsManager.getSourceService().findById(id).get();
    }    
    
    @PutMapping("/sources/{id}")
    public ResponseEntity<?> update(@RequestBody Source source, @PathVariable Long id) {
        try {
            Optional<Source> existingSource = metricsManager.getSourceService().findById(id);
            if (existingSource.isPresent()) {
            	metricsManager.getSourceService().update(existingSource.get());
            } else {
            	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            return new ResponseEntity<>(existingSource.get(),HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 
    }
    
    @DeleteMapping("/sources/{id}")
    public void delete(@PathVariable Long id) {
    	metricsManager.getSourceService().delete(id);
    }
}
