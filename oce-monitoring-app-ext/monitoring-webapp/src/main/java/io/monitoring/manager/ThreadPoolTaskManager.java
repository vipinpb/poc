/**
 * 
 */
package io.monitoring.manager;

import io.monitoring.metrics.Task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

/**
 * @author vipink
 *
 */
@Service
public class ThreadPoolTaskManager {

	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	private static final Map<Long, Task> tasks = new ConcurrentHashMap<>(6);
	
	public ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
		return threadPoolTaskScheduler;
	}
	
	public Map<Long, Task> tasks() {
		return tasks;
	}
}
