package hcl.graphql.services.rest;

import java.util.List;

import hcl.graphql.services.gqlfederation.sendorder.User;

public class UserListResponse {

	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UserListResponse(List<User> users) {
		super();
		this.users = users;
	}

	public UserListResponse() {}

	@Override
	public String toString() {
	    String results = "";
	    for(User d : users) {
	        results += "," + d.toString();
	    }
	    return results;
	}
	

}
