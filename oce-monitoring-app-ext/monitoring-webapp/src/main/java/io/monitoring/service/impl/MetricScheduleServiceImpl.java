/**
 * 
 */
package io.monitoring.service.impl;

import static io.monitoring.common.Constants.LOGGER;
import io.monitoring.domain.MetricSchedule;
import io.monitoring.domain.Source;
import io.monitoring.manager.ThreadPoolTaskManager;
import io.monitoring.metrics.Record;
import io.monitoring.metrics.Row;
import io.monitoring.metrics.Status;
import io.monitoring.metrics.Task;
import io.monitoring.repository.MetricScheduleRepository;
import io.monitoring.repository.SourceRepository;
import io.monitoring.rule.engine.RuleEngine;
import io.monitoring.service.MetricScheduleService;
import io.monitoring.service.MetricService;
import io.monitoring.source.MetricSource;
import io.monitoring.source.MetricSourceException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
/**
 * @author vipink
 *
 */
public class MetricScheduleServiceImpl implements MetricScheduleService {

	
	@Autowired
	private MetricScheduleRepository metricScheduleRepository;
	
	@Autowired
	private SourceRepository sourceRepository;
	
	@Autowired
	ThreadPoolTaskManager threadPoolTaskManager;
	
	@Autowired
	MetricService metricService;
	
	@Autowired
	RuleEngine ruleEngine;
	
	/**
	 * 
	 */
	public MetricScheduleServiceImpl() {}

	@Override
	public MetricSchedule save(MetricSchedule metricSchedule) {
		return metricScheduleRepository.save(metricSchedule);
	}

	@Override
	public List<MetricSchedule> findAll() {
		return (List<MetricSchedule>) metricScheduleRepository.findAll();
	}

	@Override
	public Optional<MetricSchedule> findById(Long id) {
		return metricScheduleRepository.findById(id);
	}
	
	public Optional<MetricSchedule> startSchedule(Long id) {
		

		Optional<MetricSchedule> mSchedule  = metricScheduleRepository.findById(id);
		if (threadPoolTaskManager.tasks().get(id) !=null ) {
			return mSchedule;
		}
		if (mSchedule.isPresent()) {
			Optional<Source>  source = sourceRepository.findById(mSchedule.get().getSourceId());
			Source src = source.get();
			String className = src.getClassname();
			String configuration = src.getConfiguration();
			RunnableTask task = new RunnableTask(
					className,
					id, 
					mSchedule.get().getThread(),
					configuration);
		    ScheduledFuture future = threadPoolTaskManager.getThreadPoolTaskScheduler().schedule(task, new 
		    PeriodicTrigger(30000));
		    task.setFuture(future);
		    threadPoolTaskManager.tasks().put(id, task);
		}
		return mSchedule;
	}
	
	public Optional<MetricSchedule> stopSchedule(Long id) {
		
		Optional<MetricSchedule> mSchedule  = metricScheduleRepository.findById(id);
		if (threadPoolTaskManager.tasks().get(id) ==null ) {
			return mSchedule;
		}
		if (mSchedule.isPresent()) {
			Iterator<Task> it = threadPoolTaskManager.tasks().values().iterator();
		    	while(it.hasNext()) {
		    		Task task = (Task)it.next();
		    		if (id.equals(task.getId())) {
		    			task.getFuture().cancel(false);
		    			it.remove();
		    		}
		      }
		    } 
		return mSchedule;
	}

	@Override
	public void delete(Long id) {
		metricScheduleRepository.deleteById(id);

	}

	@Override
	public MetricSchedule update(MetricSchedule metricSource) {
		return metricScheduleRepository.save(metricSource);
	}
	class RunnableTask implements Task {
	   
		private MetricSource metricSource;
		private Long id;
		private String name;
	    
	    public RunnableTask(
	    		String className,
	    		Long id,
	    		String name,
	    		String configuration){
	        Class<?> c;
			try {
				this.id = id;
				this.name = name;
				c = Class.forName(className);
		        Constructor<?> cons = c.getConstructor();
		        metricSource = (MetricSource) cons.newInstance();
		        metricSource.setConfiguration(configuration);
		        
			} catch (ClassNotFoundException | 
					NoSuchMethodException | 
					SecurityException | 
					InstantiationException |
					IllegalAccessException | 
					IllegalArgumentException | 
					InvocationTargetException e) {
				e.printStackTrace();
				LOGGER.error("Error loading the metric source", e);
			}
   
	    }
	    
	    private ScheduledFuture future;
	    
	    public void setFuture(ScheduledFuture future) {
	      this.future = future;
	    }
	    
	    public ScheduledFuture getFuture() {
	      return future;
	    }
	    
	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		@Override
	    public void run() {
	    	if (!Thread.currentThread().getName().equalsIgnoreCase(name)) {
	    		Thread.currentThread().setName(name);
	    	}
	        System.out.println(new Date()+" Runnable Task with "+metricSource.name()
	          +" on thread "+Thread.currentThread().getName());
	        try {
				Iterator<Record> metrics = metricSource.fetchRecords();
				MetricHelper metricHelper = new MetricHelper();
				List<Row> records = new ArrayList<>();
				while (metrics!= null && metrics.hasNext() ) {
					records.add(metricHelper.buildRow( metrics.next(),metricSource.name()));
				}
				metricService.add(records, metricSource.name());
				if (LOGGER.isDebugEnabled() ) {
					LOGGER.debug(new Date()+" Runnable Task with "+metricSource.name()
	          +" on thread "+Thread.currentThread().getName());
				}
				
			} catch (MetricSourceException e) {
				e.printStackTrace();
				LOGGER.error("Error running the metric source", e);
			}
	    }
	}
}
