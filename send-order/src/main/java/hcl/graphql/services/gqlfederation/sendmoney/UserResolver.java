package hcl.graphql.services.gqlfederation.sendmoney;

import com.coxautodev.graphql.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserResolver implements GraphQLResolver<User> {

    
    private final OrderService orderService;

    public UserResolver(final
    		OrderService orderService) {
        this.orderService = orderService;
    }

    public List<Order> orders(User user, DataFetchingEnvironment dataFetchingEnvironment) {
        return orderService.findOrdersByUserId(user.getId());
    }
}
