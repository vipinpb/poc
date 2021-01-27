package hcl.graphql.services.gqlfederation.tracktransfer;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class MoneyTransferService {

    @NotNull
	public MoneyTransfer lookupTransfer(@NotNull String mtcn) {
		return DataHelper.findTransferByMtcn(mtcn);
	}

	public Order findMoneySentByMtcn(String mtcn) {
		return DataHelper.findTransferByMtcn(mtcn).getOrder();
	}

}