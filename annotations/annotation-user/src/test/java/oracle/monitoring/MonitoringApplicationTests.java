package oracle.monitoring;


import oracle.monitoring.annotation.test.MyAnnotation;

import org.junit.jupiter.api.Test;

//@RunWith(Spring.class)
class MonitoringApplicationTests {

	
	MyAnnotation myAnnotationTest = new MyAnnotation();
	

	void validate3(MyAnnotation myAnnotationTest) {
		System.out.println("Calling validate 3");
	}

	@Test
	void validate2( ) {
		myAnnotationTest.validate1("app");
		myAnnotationTest.validate1("apple");
		myAnnotationTest.validate3("apple");
		myAnnotationTest.validate4("apple");


		System.out.println("Calling validate 2");
	}
}
