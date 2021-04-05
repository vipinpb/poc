/**
 * 
 */
package io.monitoring.source;

import io.monitoring.metrics.Record;
import io.monitoring.rest.client.AuthProvider;
import io.monitoring.rest.client.BasicAuthProvider;
import io.monitoring.rest.client.IRestConnection;
import io.monitoring.rest.client.RestContext;
import io.monitoring.rest.client.RestService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ws.rs.client.WebTarget;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author vipink
 *
 */
public class OCEWlsMetricSource implements MetricSource {

	private String authToken;
	private String host;
	private String scrapeUri;
	private String name;

	/**
	 * 
	 */
	public OCEWlsMetricSource() {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see oracle.monitoring.source.MetricSource#fetchRecords()
	 */
	@Override
	public Iterator<Record> fetchRecords() throws MetricSourceException {
		List<Record> recordList = new ArrayList<>();
		List<String> sourceList = scrapUrl();
		long timestamp = System.currentTimeMillis();
		for (String metric : sourceList) {
			// parse string one by one and create record object
			WlsExportMetricsParser p = new WlsExportMetricsParser();
			Record r = p.row(timestamp, metric);
			recordList.add(r);
		}
		return recordList.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see oracle.monitoring.source.MetricSource#name()
	 */
	@Override
	public String name() {
		return name;
	}
	public List<String> scrapUrl() {
		try {
			String uri = host + scrapeUri;
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			if (authToken != null) {
				String authStringEnc = Base64.getEncoder().encodeToString(
						authToken.getBytes());
				con.addRequestProperty("Authorization", "Basic "
						+ authStringEnc);
			}
			
			
			List<String> list = new ArrayList<>();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				list.add(inputLine);
			}
			in.close();
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ArrayList<>();
		}
	}
	public List<String> scrap() {
		String authStr = authToken;
		AuthProvider auth = new BasicAuthProvider(authStr);
		String uri = host + scrapeUri;
		RestContext context = new RestContext(auth, uri);
		try {
			List<String> l = getMetrics(context);
			return l;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private List<String> getMetrics(RestContext context) throws Exception {
		try {
			// get the rest connection
			IRestConnection conn = RestService.getRestConnection(context);
			WebTarget webTarget = conn.getClient().target(context.getBaseUrl());
			String strResponse = conn.executeGet(webTarget, String.class);
			List<String> list = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new StringReader(
					strResponse))) {
				list = br.lines().collect(Collectors.toList());
			}
			return list;

		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	@Override
	public void setConfiguration(String configuration) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			//JsonNode actualObj = mapper.readTree(configuration);
			Map<String,String> map = mapper.readValue(configuration, Map.class);
			host = map.get("host");
			scrapeUri = map.get("scrape_uri");
			authToken = map.get("auth_token");
			name = map.get("name");

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
