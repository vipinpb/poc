package io.monitoring.source;

import io.monitoring.metrics.Record;

import java.util.Iterator;

public interface MetricSource {
	 /**
	  * 
	  * @param configuration -- a JSON based configuration passed
	  * @return metrics record
	  * @throws MetricSourceException
	  */
     public Iterator<Record> fetchRecords() throws MetricSourceException;
     /**
      * Set the configuration of the source.  This is automatically called by system and registered configuration is passed
      * @param configuration
      */
     public void setConfiguration(String configuration);
     /**
      * return the name of the metric source
      * @return
      */
     public String name();
}
