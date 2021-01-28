package hcl.graphql.services.gqlfederation.tracktransfer;

import javax.annotation.PostConstruct;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcl.graphql.services.rest.MonolithicHttpClient;

@Service
public class MoneyTransferService {
	@Autowired
	private MonolithicHttpClient monolithicHttpClient;
	private MoneyTransfer moneyTransfer = null;

	@PostConstruct
	public void init() {
		moneyTransfer = monolithicHttpClient.getTransfer();
	}

	@NotNull
	public MoneyTransfer lookupTransfer(@NotNull String mtcn) {

		if (moneyTransfer != null) {
			return moneyTransfer;
		}
		return DataHelper.findTransferByMtcn(mtcn);
	}

	public Order findMoneySentByMtcn(String mtcn) {
		if (moneyTransfer != null && moneyTransfer.getOrder() != null) {
			return moneyTransfer.getOrder();
		}
		return DataHelper.findTransferByMtcn(mtcn).getOrder();
	}
}