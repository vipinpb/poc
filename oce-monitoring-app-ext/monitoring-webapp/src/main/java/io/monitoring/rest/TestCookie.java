/**
 * 
 */
package io.monitoring.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author vipink
 *
 */
public class TestCookie {

	/**
	 * 
	 */
	public TestCookie() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test();
	}
	public static void test () {
		Map<String, String> cookieHeaderMap = new HashMap();
		try {
			String sessionIdPrefix ="SS_X_";
			//String remoteUrl = "http://vvellank-m05.subnet1bom1.devcec02bom.oraclevcn.com:30305/sites";
			String remoteUrl = "http://vvellank-m01.subnet1bom1.devcec02bom.oraclevcn.com:30305/sites";

			if (remoteUrl.endsWith("/")) {
				remoteUrl = remoteUrl + "status.jsp";
			} else {
				remoteUrl = remoteUrl + "/status.jsp";
			}
			URL url = new URL(remoteUrl);


			HttpURLConnection urlConnection = null;

			urlConnection = (HttpURLConnection) url.openConnection();

			InputStream in = urlConnection.getInputStream();
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			out.write(buffer, 0, bytesRead);

			while ((bytesRead = in.read(buffer)) > 0) {
				out.write(buffer, 0, bytesRead);
			}

			out.close();
			out.flush();
			for (Map.Entry<String, List<String>> entries : urlConnection
					.getHeaderFields().entrySet()) {
				String key = entries.getKey();
				String values = "";
				for (String value : entries.getValue()) {
					values += value + ", ";
				}
				System.out.println(key);
				if ("Set-Cookie".equalsIgnoreCase(key)) {
					for (String value : entries.getValue()) {
						List<HttpCookie> cookiesList = HttpCookie.parse(value);
						for (HttpCookie c : cookiesList) {
							String cName = c.getName() ;
							if (!"JSESSIONID".equalsIgnoreCase(cName)) {
								// set up two cookies here so that it can reach to server
								// LB cookie is not shared with node. so we need to pass it to server
								cName = sessionIdPrefix + cName ;
								cookieHeaderMap.put(cName, c.getValue());
								cookieHeaderMap.put(c.getName(), c.getValue());
							} else {
								cookieHeaderMap.put(cName, c.getValue());
							}
							System.out.println(" Found the cookie "+cName +"="+ c.getValue());
						}
					}
				}
			}
			cookieHeaderMap.forEach((k,v) -> System.out.println(k + "  "+v));
				
			
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
}
