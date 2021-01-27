package hcl.graphql.services.gqlfederation.sendmoney;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private List<Order> orderList = new ArrayList<>();
 
    @PostConstruct
    public void init() {
    	orderList = DataHelper.loadOrderList();
    }

    @NotNull
    public Order lookupOrder(@NotNull String id) {
    	Order m1 = orderList.stream().filter(m -> m.getId().equals(id)).findAny().get();
        return m1;
    }


	public List<Order> findOrdersByUserId(String userId) {
		return DataHelper.findOrderByUserId(userId);
	}

	public Address findToaddressByOrderId(String id) {
		return lookupOrder(id).getToaddress();
	}

	public Address findFromaddressByOrderId(String id) {
		return lookupOrder(id).getFromaddress();
	}
}