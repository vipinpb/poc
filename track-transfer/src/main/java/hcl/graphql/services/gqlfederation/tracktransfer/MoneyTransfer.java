package hcl.graphql.services.gqlfederation.tracktransfer;

public class MoneyTransfer {

    private String mtcn;
    private String status;
    private Order order;
    private User user;
    
	public MoneyTransfer() {}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public MoneyTransfer(String mtcn, String status) {
		super();
		this.mtcn = mtcn;
		this.status = status;
	}
	public String getMtcn() {
		return mtcn;
	}
	public void setMtcn(String mtcn) {
		this.mtcn = mtcn;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}


}