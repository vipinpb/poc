package hcl.graphql.services.rest;

import hcl.graphql.services.gqlfederation.tracktransfer.MoneyTransfer;

public class MoneyTransferResponse {
	private MoneyTransfer moneyTransfer;

	public MoneyTransfer getMoneyTransfer() {
		return moneyTransfer;
	}

	public void setMoneyTransfer(MoneyTransfer moneyTransfer) {
		this.moneyTransfer = moneyTransfer;
	}

	public MoneyTransferResponse(MoneyTransfer moneyTransfer) {
		super();
		this.moneyTransfer = moneyTransfer;
	}

	public MoneyTransferResponse() {

	}
}
