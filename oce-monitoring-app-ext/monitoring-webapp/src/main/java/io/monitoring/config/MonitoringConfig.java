/**
 * 
 */
package io.monitoring.config;

import io.monitoring.common.APIConfig;
import io.monitoring.common.ConfigProperties;
import io.monitoring.common.StoreType;
import io.monitoring.manager.MetricsManager;
import io.monitoring.manager.MetricsManagerImpl;
import io.monitoring.manager.RuleManagerImpl;
import io.monitoring.manager.RulesManager;
import io.monitoring.manager.ScrapeManager;
import io.monitoring.metrics.Row;
import io.monitoring.rule.engine.RuleParser;
import io.monitoring.rule.engine.SpaceRuleParser;
import io.monitoring.service.AlertStoreService;
import io.monitoring.service.MetricScheduleService;
import io.monitoring.service.MetricService;
import io.monitoring.service.SourceService;
import io.monitoring.service.StoreService;
import io.monitoring.service.impl.MetricScheduleServiceImpl;
import io.monitoring.service.impl.MetricServiceImpl;
import io.monitoring.service.impl.ScrapeManagerImpl;
import io.monitoring.service.impl.SourceServiceImpl;
import io.monitoring.service.impl.SystemAlertStoreService;
import io.monitoring.store.HSQLDBStoreService;
import io.monitoring.store.MemoryStoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * This would act like service locator and create all the beans needed to run the monitoring
 * using annotation.
 * 
 * @TODO
 * ConfigProperties would be extended to switch the different implementation of various beans 
 * 
 * @author vipink
 *
 */
@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class MonitoringConfig { 

    @Autowired
    private ConfigProperties monitoringProperties;
	/**
	 * 
	 */
	public MonitoringConfig() {}

	@Bean
	public MetricsManager metricsManager() {
		return new MetricsManagerImpl();
	}

	@Bean
	public AlertStoreService alertStoreService() {
		return new SystemAlertStoreService();
	}
	
	
	@Bean
	public ScrapeManager scrapeManager() {
		return new ScrapeManagerImpl();
	}
	
	@Bean
	public StoreService<?> storeService() {
		StoreType type = APIConfig.storeType ;
		StoreService<?> srv = null;
		switch (type) {
		case MEMORY:
			srv = new MemoryStoreService();
			break;
		case HSQLDB:
			srv = new HSQLDBStoreService();
			break;
		default:
			srv = new HSQLDBStoreService();
		}
		return srv;
	}
	
	@Bean
	public RulesManager ruleManager() {
		return new RuleManagerImpl();
	}
	
	@Bean
	public SourceService sourceService() {
		return new SourceServiceImpl();
	}
	
	@Bean
	public MetricScheduleService metricScheduleService() {
		return new MetricScheduleServiceImpl();
	}
	
	@Bean
	public MetricService metricService() {
		return new MetricServiceImpl();
	}
	
	@Bean
	public RuleParser ruleParser() {
		return new SpaceRuleParser();
	}
	
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler
          = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix(
          "MonitoringTaskScheduler");
        return threadPoolTaskScheduler;
    }
}
