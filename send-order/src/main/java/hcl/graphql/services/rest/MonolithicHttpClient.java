package hcl.graphql.services.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hcl.graphql.services.gqlfederation.sendorder.User;

@Component
public class MonolithicHttpClient {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${fetch.users.url}")
	private String usersUrl;

	public List<User> getUsers() {
		UserListResponse userListResponse = null;
		try {
			userListResponse = restTemplate.getForObject(usersUrl, UserListResponse.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return userListResponse != null ? userListResponse.getUsers() : null;

	}
}
