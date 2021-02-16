/**
 * 
 */
package oracle.monitoring.graphql.pojo;

import oracle.monitoring.annotation.graphql.VGraphQLType;

/**
 * @author vipink
 *
 */
@VGraphQLType (name="user")
public class User {

	/**
	 * 
	 */
	public User() {
		// TODO Auto-generated constructor stub
	}
	
	private String firstName;
	private String id;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	

}
