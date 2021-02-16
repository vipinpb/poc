/**
 * 
 */
package hcl.graphql.pojo;

import hcl.annotation.graphql.VGraphQLType;

/**
 * @author vipink
 *
 */
@VGraphQLType (name="order")
public class Order {

	/**
	 * 
	 */
	public Order() {
		// TODO Auto-generated constructor stub
	}

	private String color;
	private String id;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
