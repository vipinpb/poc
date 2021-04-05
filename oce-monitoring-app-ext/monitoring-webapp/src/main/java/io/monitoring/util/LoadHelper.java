/**
 * 
 */
package io.monitoring.util;

import io.monitoring.common.Util;
import io.monitoring.domain.MetricAlert;
import io.monitoring.domain.MetricRule;
import io.monitoring.repository.MetricAlertRepository;
import io.monitoring.repository.MetricRuleRepository;
import io.monitoring.rest.client.IRestConnection;
import io.monitoring.rest.client.RestContext;
import io.monitoring.rest.client.RestService;
import io.monitoring.rule.engine.IRule;
import io.monitoring.service.AlertStoreService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.client.WebTarget;

import oracle.monitoring.metrics.Alert;
import oracle.monitoring.store.service.AlertException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vipink
 *
 */
@Service
public class LoadHelper {

	@Autowired
	AlertStoreService alertStoreService;
	
	@Autowired
	MetricAlertRepository metricAlertRepository;
	
	@Autowired
	MetricRuleRepository metricRuleRepository;
	/**
	 * 
	 */
	public LoadHelper() {}

	
	public List<String> generateSession(String baseUrl) {
		return scrapUrl(baseUrl+"/api/v1/test/createsession");
	}

	private List<String> getData(RestContext context)
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
	
	public List<String> scrapUrl(String uri) {
		try {
			URL url = new URL(uri);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
		
			
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
			return new ArrayList<>();
		}
	}


	public List<Alert> generateAlerts() {

		List<Alert> alerts = new ArrayList<>();
		Iterable<MetricRule> metricRules = getRules();
		Iterable<IRule> rules= Util.buildRules(metricRules);
		for (MetricRule r : metricRules) {
			Iterable<MetricAlert> metricAlerts = metricAlertRepository.findByRuleId(r.getId());
			for (MetricAlert a : metricAlerts) {
				Alert alert = new Alert(a.getName(),r.getExp(), 1);
				alert.setValue("20");
				alerts.add(alert);
				try {
					alertStoreService.store(alert);
				} catch (AlertException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		Thread gen = new Thread(new GenerateAlerts());
		gen.start();
		
		return alerts;
	}
	
	public class GenerateAlerts implements Runnable {
		@Override
		public void run() {
			try {
				Thread.currentThread().sleep(120000);
				System.out.println("cleaning the fake alerts");

				Iterable<MetricRule> metricRules = getRules();
				Iterable<IRule> rules = Util.buildRules(metricRules);
				for (MetricRule r : metricRules) {
					Iterable<MetricAlert> metricAlerts = metricAlertRepository
							.findByRuleId(r.getId());
					for (MetricAlert a : metricAlerts) {
						Alert alert = new Alert(a.getName(), r.getExp(), 0);
						alert.setValue("10");
						alertStoreService.store(alert);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public Iterable<MetricRule> getRules() {
		List<MetricRule> rules = new ArrayList<>();
		MetricRule metricRule = new MetricRule();
		metricRule.setId(9L);
		rules.add(metricRule);
		metricRule = new MetricRule();
		metricRule.setId(10L);
		rules.add(metricRule);
		metricRule = new MetricRule();
		metricRule.setId(11L);
		rules.add(metricRule);
		metricRule = new MetricRule();
		metricRule.setId(12L);
		rules.add(metricRule);
		metricRule = new MetricRule();
		metricRule.setId(1L);
		rules.add(metricRule);
		return rules;
		
	}
}
