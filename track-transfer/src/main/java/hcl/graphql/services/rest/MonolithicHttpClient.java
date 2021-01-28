package hcl.graphql.services.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import hcl.graphql.services.gqlfederation.tracktransfer.MoneyTransfer;
import hcl.graphql.services.gqlfederation.tracktransfer.Order;
import hcl.graphql.services.gqlfederation.tracktransfer.User;

@Component
public class MonolithicHttpClient {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${fetch.transfer.url}")
	private String transferURL;

	@Value("${fetch.users.url}")
	private String usersUrl;

	@Value("${fetch.orders.url}")
	private String ordersURL;

	public MoneyTransfer getTransfer() {
		MoneyTransferResponse moneyTransferResponse = null;
		try {
			moneyTransferResponse = restTemplate.getForObject(transferURL, MoneyTransferResponse.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return moneyTransferResponse != null ? moneyTransferResponse.getMoneyTransfer() : null;

	}

	public List<User> getUsers() {
		try {
			return restTemplate.getForObject(usersUrl, UserResponse.class).getUsers();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public List<Order> getOrder() {
		OrderResponse orderResponse = null;
		try {
			orderResponse = restTemplate.getForObject(ordersURL, OrderResponse.class);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return orderResponse != null ? orderResponse.getOrders() : null;

	}
}
