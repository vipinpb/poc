package hcl.graphql.services.rest;

import java.util.List;

import hcl.graphql.services.gqlfederation.tracktransfer.Order;

public class OrderResponse {

	private List<Order> orders;

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public OrderResponse(List<Order> orders) {
		super();
		this.orders = orders;
	}
	public OrderResponse() {

	}
}
