package hcl.graphql.services.gqlfederation.sendmoney;

import com.coxautodev.graphql.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Service;

@Service
public class OrderResolver implements GraphQLResolver<Order> {

    private final OrderService orderService;

    public OrderResolver(final OrderService orderService) {
        this.orderService = orderService;
    }

    public Address toaddress(Order ms, DataFetchingEnvironment dataFetchingEnvironment) {
        return orderService.findToaddressByOrderId(ms.getId());
    }
    
    public Address fromaddress(Order ms, DataFetchingEnvironment dataFetchingEnvironment) {
        return orderService.findFromaddressByOrderId(ms.getId());
    }
}
