package hcl.graphql.services.gqlfederation.tracktransfer;

public class Order {

    private String id;
    private String amount ;

    public Order() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Order(String id, String amount) {
		super();
		this.id = id;
		this.amount = amount;
	}
    
    
}