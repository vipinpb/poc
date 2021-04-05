package io.monitoring.aop.service.test;

import io.monitoring.annotation.ExecutionTime;
import io.monitoring.annotation.MonitorError;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Service {

    @ExecutionTime
    public void serve() throws InterruptedException {
    	System.out.println();
        Thread.sleep(2000);
    }
    
    public List<String> listNames() {
    	List<String> arrayList = new ArrayList<>();
    	arrayList.add("Vipin");
    	arrayList.add("San");
    	return arrayList;
    }
    
    @MonitorError(key="oce_oml_service_status", count=3, exception=Exception.class)
    public void test() throws Exception {
    	try {
    
    		throw new Exception("MY error");
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		throw new Exception("Ok error");
    	}
    }
}
