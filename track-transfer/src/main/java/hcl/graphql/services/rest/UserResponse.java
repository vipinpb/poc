package hcl.graphql.services.rest;

import java.util.List;

import hcl.graphql.services.gqlfederation.tracktransfer.User;

public class UserResponse {

	private List<User> users;

	
	public UserResponse(List<User> users) {
		super();
		this.users = users;
	}


	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}


	public UserResponse() {

	}
}
