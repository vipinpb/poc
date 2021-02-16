package hcl.graphql.services.gqlfederation.sendorder;

public class Order {

    private String id;
    private String username;
    private Address toaddress;
    private Address fromaddress;
    private String amount ;

    public Order() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Address getToaddress() {
		return toaddress;
	}

	public void setToaddress(Address toaddress) {
		this.toaddress = toaddress;
	}

	public Address getFromaddress() {
		return fromaddress;
	}

	public void setFromaddress(Address fromaddress) {
		this.fromaddress = fromaddress;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Order(String id, String username, Address toaddress, Address fromaddress, String amount) {
		super();
		this.id = id;
		this.username = username;
		this.toaddress = toaddress;
		this.fromaddress = fromaddress;
		this.amount = amount;
	}
    
    
}