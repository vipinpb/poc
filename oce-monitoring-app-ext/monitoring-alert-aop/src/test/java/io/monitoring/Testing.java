package io.monitoring;

import io.monitoring.aop.service.MethodMetaData;
import io.monitoring.aop.service.RegistryService;
import io.monitoring.aop.service.test.Job;
import io.monitoring.aop.service.test.Service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class Testing {

    @Autowired
    private Service service;
    @Autowired
    RegistryService callableRegistryService;
    
    @Autowired
    Job job;
	
	public Testing() {
	}

	@Test
	public void test(){
		try {
			service.serve();
			
			service.test();
			job.apple("apple is great");
			job.mango("mango is great");
			//job.session("session is great");
			
			MethodMetaData metaData = callableRegistryService.poll();
			
			if (metaData != null) {
				System.out.println(" testing test method");
				metaData.getMethod().invoke(metaData.getTarget(), metaData.getArgs());
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
