/**
 * 
 */
package io.monitoring.metrics;

import java.util.concurrent.ScheduledFuture;

/**
 * @author vipink
 *
 */
public interface Task extends Runnable {
	ScheduledFuture getFuture() ;
	Long getId();
}
