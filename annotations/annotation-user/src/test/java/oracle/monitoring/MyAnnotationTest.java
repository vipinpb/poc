package oracle.monitoring;
/**
 * 
 */


import org.junit.Test;

import oracle.monitoring.annotation.CanExecute;
import oracle.monitoring.annotation.ClassCheckRun;
import oracle.monitoring.annotation.SourceCheckRun;
import oracle.monitoring.annotation.test.MyAnnotation;

/**
 * @author vipink
 *
 */
public class MyAnnotationTest {

	/**
	 * 
	 */
	public MyAnnotationTest() {
		// TODO Auto-generated constructor stub
	}
 
	@Test
	public void validate1() {
		System.out.println("Calling validate 1");
		MyAnnotation m= new MyAnnotation();
		m.validate1("PK");
	}
	
	@Test
	public void validate3() {
		System.out.println("Calling validate class ");
		MyAnnotation m= new MyAnnotation();
		m.validate3("PK");
	}
	
	@Test
	public void validate4() {
		
		System.out.println("Calling validate source");
		MyAnnotation m= new MyAnnotation();
		m.validate4("PK");
	}
}
