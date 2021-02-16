package hcl.graphql.services.gqlfederation.sendorder;

import java.util.List;
import java.util.Objects;


public class User {

    private String id;
    private String name;
    private String username;
    private List<Order> orders;

    public User() {}

    public User(final String id, final String username) {
        this.id = id;
        this.username = username;
    }

    public User(final String id, final String name, final String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public User(String id, String name, String username, List<Order> orders) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.orders = orders;
	}

	public List<Order> getOrders() {
		return orders; 
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(username, user.username) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username);
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", orders=" + orders + "]";
	}
    
    
}