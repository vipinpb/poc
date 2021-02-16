/**
 * 
 */
package hcl.annotation.graphql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author vipink
 *
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE )
public @interface VGraphQLType {
	String name() default "";
	String description() default "";
	String [] fieldOrder() default {};
	String client() default "apollo";
	String key() default "";
}
