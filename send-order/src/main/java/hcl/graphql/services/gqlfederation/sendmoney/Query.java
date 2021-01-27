package hcl.graphql.services.gqlfederation.sendmoney;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Service;

@Service
public class Query implements GraphQLQueryResolver {

    private final UserService userService;
    
    private final OrderService orderService;

    public Query(final UserService userService,
    		final OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    public User me (Integer userId, final DataFetchingEnvironment dataFetchingEnvironment) {
        return userService.lookupUser(String.valueOf(userId));
    }
    
    public Order order (Integer orderId, final DataFetchingEnvironment dataFetchingEnvironment) {
        return orderService.lookupOrder(String.valueOf(orderId));
    }
}

