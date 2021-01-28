package hcl.graphql.services.gqlfederation.sendorder;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcl.graphql.services.rest.MonolithicHttpClient;

@Service
public class OrderService {

	private List<Order> orderList = new ArrayList<>();
	@Autowired
	private MonolithicHttpClient monolithicHttpClient;

	@PostConstruct
	public void init() {
		if (orderList.size() <= 0) {
			List<User> users = monolithicHttpClient.getUsers();
			if (users == null || users.size() <= 0) {
				orderList = DataHelper.loadOrderList();
			} else {
				for (User user : users) {
					for (Order order : user.getOrders()) {
						orderList.add(order);
					}
				}
			}
		}

	}

	@NotNull
	public Order lookupOrder(@NotNull String id) {

		List<Order> orders = getOrders(id);
		if (orders != null && !orders.isEmpty()) {
			return orders.stream().filter(m -> m.getId().equals(id)).findAny().get();
		}
		return orderList.stream().filter(m -> m.getId().equals(id)).findAny().get();
	}

	public List<Order> findOrdersByUserId(String userId) {

		List<Order> orders = getOrders(userId);
		if (orders != null) {
			return orders;
		}
		return DataHelper.findOrderByUserId(userId);
	}

	public Address findToaddressByOrderId(String id) {

		List<Order> orders = getOrders(id);
		if (orders != null && !orders.isEmpty()) {
			Order order = orders.stream().filter(m -> m.getId().equals(id)).findAny().get();
			if (order != null && order.getToaddress() != null) {
				return order.getToaddress();
			}
		}

		return lookupOrder(id).getToaddress();
	}

	public Address findFromaddressByOrderId(String id) {
		return lookupOrder(id).getFromaddress();
	}

	private List<Order> getOrders(String userId) {
		return orderList;
	}
}