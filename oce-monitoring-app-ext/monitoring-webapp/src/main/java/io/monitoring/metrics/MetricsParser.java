/**
 * 
 */
package io.monitoring.metrics;


/**
 * @author vipink
 *
 */
public class MetricsParser {

	/**
	 * 
	 */
	public MetricsParser() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input= "weblogic_servlet_execution_time_low{app=\"Documents\",name=\"cec_server_1_\",servletName=\"FileServlet\"} 0";
		int labelIndex = input.indexOf('{');
		if (labelIndex >= 0 ) {
			String metricName = input.substring(0, labelIndex);
			System.out.println(metricName);
			final StringBuilder keyBuilder = new StringBuilder(metricName);
			String labels = input.substring(labelIndex+1, input.indexOf('}'));
			System.out.println(labels);
			String label[] = labels.split(",");
			for (String l: label) {
				String nameValue [] = l.split("=");
				keyBuilder.append("#"+nameValue[0].toLowerCase()+":"+nameValue[1].replaceAll("\"", "").toLowerCase());
			}
			System.out.println(keyBuilder.toString());
			String values = input.substring(input.indexOf('}')+1);
			System.out.println(" IN "+values);
			String tokenValues[] = values.trim().split(" ");
			if (tokenValues != null && tokenValues.length ==2) {
				System.out.println("VAL 1 " +tokenValues[0]);
				System.out.println(tokenValues[1]);
			} else if (tokenValues != null && tokenValues.length ==1) {
				System.out.println("VAL "+ tokenValues[0].trim());
				System.out.println("VAL "+ tokenValues[0]);

			}
		} else {
			
		}
	}

	
	public Row row(long timestamp, String metric) {
		Row row = new Row(timestamp);
		String input= metric;
		int labelIndex = input.indexOf('{');
		if (labelIndex >= 0 ) {
			String metricName = input.substring(0, labelIndex);
			final StringBuilder keyBuilder = new StringBuilder(metricName);
			String labels = input.substring(labelIndex+1, input.indexOf('}'));
			System.out.println(labels);
			String label[] = labels.split(",");
			for (String l: label) {
				String nameValue [] = l.split("=");
				keyBuilder.append("#"+nameValue[0].toLowerCase()+":"+nameValue[1].replaceAll("\"", "").toLowerCase());
			}
			String values = input.substring(input.indexOf('}')+1);
			row.setKey(keyBuilder.toString());
			String tokenValues[] = values.trim().split(" ");
			if (tokenValues != null && tokenValues.length ==2) {
				row.setValue(tokenValues[0]);
			} else if (tokenValues != null && tokenValues.length ==1) {
				row.setValue(tokenValues[0]);
			}
		} else {
			String tokenValues[] = input.trim().split(" ");
			if (tokenValues != null && tokenValues.length ==3) {
				row.setKey(tokenValues[0]);
				row.setValue(tokenValues[1]);
			} else if (tokenValues != null && tokenValues.length ==2) {
				row.setKey(tokenValues[0]);
				row.setValue(tokenValues[1]);
			}
		}
		
		return row;
		
	}
	
}
