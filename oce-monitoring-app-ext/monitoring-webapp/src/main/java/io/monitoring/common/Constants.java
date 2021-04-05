package io.monitoring.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Constants {
    
    public static final String MONITORING_LOGGER = "oracle.monitoring.common";
    public static Log LOGGER = LogFactory.getLog(MONITORING_LOGGER);
    public static final String MONITORING_ALERT_LOGGER = "oracle.monitoring.common.alert";
    public static Log ALERT_LOGGER = LogFactory.getLog(MONITORING_LOGGER);

}
