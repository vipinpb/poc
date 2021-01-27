package hcl.graphql.services.gqlfederation.tracktransfer;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;

import org.springframework.stereotype.Service;

@Service
public class Query implements GraphQLQueryResolver {

    private final UserService userService;
    
    private final OrderService moneySentService;
    
    private final MoneyTransferService transferService;

    public Query(final UserService userService,
    		final OrderService moneySentService,
    		MoneyTransferService transferService) {
        this.userService = userService;
        this.moneySentService = moneySentService;
        this.transferService = transferService;
    }
    
    public MoneyTransfer tracktransfer(String mtcn, DataFetchingEnvironment dataFetchingEnvironment) {
        return transferService.lookupTransfer(mtcn);
    }
}