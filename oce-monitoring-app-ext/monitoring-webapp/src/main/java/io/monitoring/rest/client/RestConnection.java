/**
 * 
 */
package io.monitoring.rest.client;

import io.monitoring.rest.Config;

import java.util.List;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

import static io.monitoring.common.Constants.LOGGER;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.ClientResponse;
 

/**
 * @author vipink 
 *
 */
public class RestConnection implements IRestConnection {

	private Client client;

	private AuthProvider authProvider;
	private Config config;
	
	public WebTarget getResource(String url) {
		return getClient().target(url);
	}

	/**
	 * Get the cached client for executing requests. If the client
	 * has not been previously cached, this method will call 
	 * {@link #createClient()} to create a new client and then
	 * cache it.
	 * @return Cache {@link Client} instance if available, new {@link Client} instance otherwise
	 */
	public final Client getClient() {
		if ( client == null ) {
			client = createClient();
		}
		return client;
	}

	public Client createClient(){

	      ClientConfig clientConfig = new ClientConfig();
     
	      if (LOGGER.isDebugEnabled()) {
	    	  LOGGER.debug("Found the configuration " +config);
	      }
	      if (config != null && config.getUseProxy() )
	      if ( config.getProxy() != null) {
	    	  if (config.getProxy().getUri() != null) {
	    		  clientConfig.property(ClientProperties.PROXY_URI, config.getProxy().getUri());
	    	  }
	    	  if (config.getProxy().getUserName() != null ) {
	    		  clientConfig.property(ClientProperties.PROXY_USERNAME, config.getProxy().getUserName());
	    	  }
	    	  if (config.getProxy().getPassword() != null) {
	    		  clientConfig.property(ClientProperties.PROXY_PASSWORD, config.getProxy().getPassword());
	    	  }
		  }
	      clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 2000);
          clientConfig.property(ClientProperties.READ_TIMEOUT, 6000);
	      ClientBuilder builder = ClientBuilder.newBuilder();
	      Client client = builder.withConfig(clientConfig).build();
	      return client;
	}
	
	
	public RestConnection(AuthProvider authProvider) {
		this.authProvider = authProvider ;
	}
	
	public RestConnection(RestContext context) {
		this.authProvider = context.getAuthProvider() ;
		this.config = context.getConfig();
	}

	public <T> T executeGet(WebTarget webResource, Class<T> returnType) {
		return executeRequest(HttpMethod.GET, webResource, null, returnType);
	}
	
	public <T> T executePost(WebTarget webResource, Entity<?> entity, Class<T> returnType) {
		return executeRequest(HttpMethod.POST, webResource, entity, returnType);
	}
	
	public <T> T executePut(WebTarget webResource, Entity<?> entity,  Class<T> returnType) {
		return executeRequest(HttpMethod.PUT, webResource, entity, returnType);
	}
	
	public <T> T executeRequest(String httpMethod, WebTarget webResource, Class<T> returnType) {
		return executeRequest(httpMethod, webResource, null, returnType);
	}
	
	public <T> T executeDelete(WebTarget webResource, Class<T> returnType) {
		return executeRequest(HttpMethod.DELETE, webResource, null, returnType);
	}
	
	public <T> T executeRequest(String httpMethod, WebTarget webResource, Entity<?> entity, Class<T> returnType) {
        return executeRequest(httpMethod, webResource.request(), entity, returnType);
	}
	
	/**
	 * Execute a request for the given method using the given builder.
	 * @param httpMethod The HTTP method to be used, as specified by one of the constants
	 *                   in {@link HttpMethod}
	 * @param builder	 The builder used to execute the request. 
	 * @param returnType The return type for the data returned by the request.
	 * @return The result of executing the HTTP request.
	 */
	private <T> T executeRequest(String httpMethod, Builder builder, Entity<?> entity, Class<T> returnType) {
		Response response = null;
		try {
			builder = manageBuilder(builder);
			response = builder.build(httpMethod, entity).invoke();
			return checkResponseAndGetOutput(httpMethod, builder, response, returnType);
		} catch ( ClientErrorException e ) {
			throw new RuntimeException("Error accessing remote system:\n"+e.getMessage(), e);
		} finally {
			if ( response != null ) { response.close(); }
		}
	}

	/**
	 * This would be used to manage any other information related to auth etc
	 * @param builder
	 * @return
	 */
	protected Builder manageBuilder(Builder builder) {
		if (authProvider != null) {
			switch (authProvider.getAuthType()) {
			case BASIC:
				String authHeader = "Basic " + authProvider.getToken().getValue();
				if (authProvider.getToken().getType() == 1) {
					builder.header(HttpHeaders.AUTHORIZATION, authHeader);
				}
				break;
			case OAUTH:
				
			case BEARER:
				authHeader = "Bearer " + authProvider.getToken().getValue();
				if (authProvider.getToken().getType() == 1) {
					builder.header(HttpHeaders.AUTHORIZATION, authHeader);
				}
				if (LOGGER.isDebugEnabled()) {
					LOGGER
							.debug("Adding the bearer header " + authHeader);
				}
				break;
				default:
					
			}

		}
		return builder;
	}

	/**
	 * Check the response code. If successful, return the entity with the given return type,
	 * otherwise throw an exception.
	 * @param response
	 * @param returnType
	 * @return The entity from the given {@link ClientResponse} if available
	 */
	protected <T> T checkResponseAndGetOutput(String httpMethod, Builder builder, Response response, Class<T> returnType) {
		StatusType status = response.getStatusInfo();
	    if (LOGGER.isDebugEnabled()) {
	    	LOGGER.debug(builder.toString()+ " response for request " +response.getStatus());
	    }
		if ( status != null && status.getFamily() == Family.SUCCESSFUL ) {
			return getSuccessfulResponse(response, returnType, status);
		} else {
			throw getUnsuccesfulResponseException(response , builder);
		}
	}

	/**
	 * Get the return value for a successful response.
	 * @param response
	 * @param returnType
	 * @param status
	 * @return
	 */
	protected <T> T getSuccessfulResponse(Response response, Class<T> returnType, StatusType status) {
		if ( returnType == null || status.getStatusCode() == Status.NO_CONTENT.getStatusCode() ) {
			return null;
		} else if ( returnType.isAssignableFrom(response.getClass()) ) {
			return (T)response;
		} else {
			return response.readEntity(returnType);
		}
	}
	
	protected RuntimeException getUnsuccesfulResponseException(Response response, Builder builder) {
		String reasonPhrase = getReasonPhrase(response);
		String msg = "Error accessing remote system "+builder.toString()+": "+reasonPhrase;
		String longMsg = msg+", response contents: \n"+response.readEntity(String.class);
		return new RuntimeException(msg, new Exception(longMsg));
	}
	

	/** 
	 * Jersey uses hard-coded reason phrases, so we try to read the 
	 * reason phrase(s) directly from the headers first.
	 * Get the reason phrase from the response or status info.
	 * @param response to get the reason phrase from
	 * @return Reason phrase
	 */
	private String getReasonPhrase(Response response) {
		List<String> reasonPhrases = response.getStringHeaders().get("Reason-Phrase");
		StatusType status = response.getStatusInfo();
		String reasonPhrase;
		if ( reasonPhrases!=null&&reasonPhrases.size()>0 ) {
			reasonPhrase = reasonPhrases.toString();
		} else if ( status != null ) {
			reasonPhrase = status.getReasonPhrase();
		} else {
			reasonPhrase = response.toString();
		}
		return reasonPhrase;
	}
}
