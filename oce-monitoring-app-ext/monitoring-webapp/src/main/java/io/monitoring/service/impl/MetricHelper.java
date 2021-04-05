/**
 * 
 */
package io.monitoring.service.impl;

import io.monitoring.metrics.Record;
import io.monitoring.metrics.Row;
import io.monitoring.rest.client.AuthProvider;
import io.monitoring.rest.client.BasicAuthProvider;
import io.monitoring.rest.client.IRestConnection;
import io.monitoring.rest.client.RestContext;
import io.monitoring.rest.client.RestService;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author vipink
 *
 */
public class MetricHelper { 

	public Row buildRow(Record record, String sourceName) {
		String labels = record.getLabels() != null ? convert(record.getLabels()) : "";
		Row row = new Row(record.getTimestamp(), record.getKey(), labels, record.getValue(),sourceName);
		return row ;
	}
	
	private String convert(Map<String, String> map) {
	    String mapAsString = map.keySet().stream()
	      .map(key -> key + "=" + map.get(key))
	      .collect(Collectors.joining(", ", "", ""));
	    return mapAsString;
	}
	/**
	 * @param args
	 */
	public static List<String> scrap() {
    	String authStr = "weblogic:Welcome1";
		AuthProvider auth = new BasicAuthProvider(authStr);
		RestContext context = new RestContext(auth, "http://ssvrint-oracle.vipin-1.cec.oraclecorp.com:8080/wls-exporter/metrics") ;
		try {
			List<String> l = getMetrics(context);
			//for (String ab: l) {
				//System.out.println(ab);
			//}
			System.out.println("Scrapping round");
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static List<String> getMetrics(RestContext context)
			throws Exception {
		try {
			// get the rest connection
			IRestConnection conn = RestService.getRestConnection(context);
			WebTarget webTarget = conn.getClient().target(context.getBaseUrl());
			String strResponse = conn.executeGet(webTarget, String.class);
			List<String> list = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new StringReader(strResponse))) {
			    list = br.lines().collect(Collectors.toList());    
			}
			return list;

		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}
}
