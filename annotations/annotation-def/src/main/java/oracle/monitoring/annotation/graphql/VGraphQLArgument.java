package oracle.monitoring.annotation.graphql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.PARAMETER })
public @interface VGraphQLArgument {
	String name() default "";
	String description() default "";
	boolean required() default false;
	String client() default "apollo";
}
