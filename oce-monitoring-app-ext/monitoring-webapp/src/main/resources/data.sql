insert into metric_source values(10, 'OCE-Exporter','Weblogic Exporter','io.monitoring.source.OCEWlsMetricSource',
'{"host":"http://localhost:8080",
  "scrape_uri":"/wls-exporter/metrics",
  "scrape.request-timeout":60,
  "scrape_socket-timeout": 10,
  "auth-token": "weblogic:Welcome1",
  "name": "OCE-Exporter"
}',false);
insert into metric_source values(20, 'MonitoringApp-Exporter','Monitoring Exporter','io.monitoring.source.MonitoringAppMetricSource',
'{"host":"http://localhost:8080",
  "scrape_uri":"/actuator/prometheus",
  "scrape.request-timeout":60,
  "scrape_socket-timeout": 10,
  "auth-token": "Basic 234024022323242",
  "name": "MonitoringApp-Exporter"
}',false);

insert into metric_schedule values(1, 10,'OCE-Exporter','{
  "scrape.interval":60,
  "scrape_timeout": 10
}');
insert into metric_schedule values(2, 20,'MonitoringApp-Exporter','{
  "scrape.interval":60,
  "scrape_timeout": 10
}');

insert into metric_rules values(1, 'cpu','process_cpu_usage > .4','1m',20,'alert');
insert into metric_rules values(2, 'FileDescriptor','process_files_open_files > 3000','1m',20,'alert');
insert into metric_rules values(3, 'HighHeap','heapFreePercent < 20','10m',10,'alert');
insert into metric_rules values(4, 'Memory','jvm_memory_used_bytes > 906137121','5m',20,'alert');
insert into metric_rules values(5, 'ThreadCount','jvm_threads_peak_threads > 271','1m',20,'alert');
insert into metric_rules values(6, 'Session','webapp_config_openSessionsHighCount > 90','10m',20,'alert');
insert into metric_rules values(7, 'BlockedThread','jvm_threads_states_threads{state="blocked"} > 200','1m',20,'alert');
insert into metric_rules values(8, 'ServletAverageExecutionTime','weblogic_servlet_executionTimeAverage{server="cec_server_1",app="CaaS",name="cec_server_1_/content",servletName="FileServlet"} > 50','1m',10,'alert');
insert into metric_rules values(9, 'ElasticSearchUp','elastic_search_health = 0','10m',20,'alert');
insert into metric_rules values(10, 'ElasticSearchCorruptIndex','elastic_search_index_health = 0','10m',20,'alert');
insert into metric_rules values(11, 'OmlServiceUp','oml_service_health = 0','10m',20,'alert');
insert into metric_rules values(12, 'DbUp','db_service_health = 0','10m',20,'alert');


insert into metric_alerts values(1,'cpu',1);
insert into metric_alerts values(2,'FileDescriptor',2);
insert into metric_alerts values(3,'HighHeap',3);
insert into metric_alerts values(4,'Memory',4);
insert into metric_alerts values(5,'ThreadCount',5);
insert into metric_alerts values(6,'Session',6);
insert into metric_alerts values(7,'BlockedThread',7);
insert into metric_alerts values(8,'ServletAverageExecutionTime',8);
insert into metric_alerts values(9,'ElasticSearchUp',9);
insert into metric_alerts values(10,'ElasticSearchCorruptIndex',10);
insert into metric_alerts values(11,'OmlServiceUp',11);
insert into metric_alerts values(12,'DbUp',12);



