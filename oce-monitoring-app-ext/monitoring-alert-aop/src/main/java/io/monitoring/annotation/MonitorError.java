/**
 * 
 */
package io.monitoring.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * this would be tracking all the external service if they are down.
 * This would notify to alert system if there is any service down
 * @author vipink
 *
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface MonitorError {
   String key() default "";
   int count() default 3;
   Class<? extends Exception> exception() default Exception.class;
}
