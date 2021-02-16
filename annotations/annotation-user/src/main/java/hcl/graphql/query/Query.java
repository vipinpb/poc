/**
 * 
 */
package hcl.graphql.query;

import hcl.annotation.graphql.VGraphQLArgument;
import hcl.annotation.graphql.VGraphQLQuery;
import hcl.graphql.pojo.Order;
import hcl.graphql.pojo.User;

/**
 * @author vipink
 *
 */
public class Query {

	/**
	 * 
	 */
	public Query() {}
	
	@VGraphQLQuery(name ="me", description="me")
	public User me(@VGraphQLArgument(name ="userId", description="userId", required=true) String userId) {
		return new User();
	}
	
	@VGraphQLQuery(name ="order", description="order")
	public Order order(@VGraphQLArgument(name ="orderId", description="orderId", required=true) String orderId) {
		return new Order();
	}

}
