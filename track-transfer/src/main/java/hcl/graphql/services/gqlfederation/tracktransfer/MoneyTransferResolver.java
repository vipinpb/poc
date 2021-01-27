package hcl.graphql.services.gqlfederation.tracktransfer;

import com.coxautodev.graphql.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransferResolver implements GraphQLResolver<MoneyTransfer> {

    private final MoneyTransferService moneyTransferService;

    public MoneyTransferResolver(final MoneyTransferService moneyTransferService) {
        this.moneyTransferService = moneyTransferService;
    }

    public Order order(MoneyTransfer transfer, DataFetchingEnvironment dataFetchingEnvironment) {
        return moneyTransferService.findMoneySentByMtcn(transfer.getMtcn());
    }
    
}
