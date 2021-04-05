/**
 * 
 */
package io.monitoring.rest.client;


/**
 * @author vipink
 *
 */
public class RestService {

	public static IRestConnection getRestConnection(RestContext context) {
		return new RestConnection(context);
	}

}
