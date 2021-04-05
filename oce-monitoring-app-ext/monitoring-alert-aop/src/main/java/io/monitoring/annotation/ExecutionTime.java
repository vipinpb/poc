/**
 * 
 */
package io.monitoring.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;; 

/**
 * @author vipink
 *
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface ExecutionTime {}
