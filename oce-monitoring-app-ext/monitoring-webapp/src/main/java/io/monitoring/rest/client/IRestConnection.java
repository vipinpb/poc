/**
 * 
 */
package io.monitoring.rest.client;

/**
 * @author vipink
 *
 */
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

public interface IRestConnection {
	
	public Client getClient(); 
	
	public <T> T executeGet(WebTarget webResource, Class<T> returnType); 
	
	public <T> T executePost(WebTarget webResource, Entity<?> entity, Class<T> returnType);
	
	public <T> T executePut(WebTarget webResource, Entity<?> entity,  Class<T> returnType) ;
	
	public <T> T executeDelete(WebTarget webResource, Class<T> returnType);
}
