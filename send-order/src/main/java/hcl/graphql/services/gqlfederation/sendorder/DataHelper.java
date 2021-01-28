/**
 * 
 */
package hcl.graphql.services.gqlfederation.sendorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author developer
 *
 */
public class DataHelper {
  static List<Order> orderList = new ArrayList<>();
  static Map<String, List<Order>> orderListStore = new HashMap<>();
  static List<User> userStoreList = new ArrayList<>();
  
  static {
      User user1 = new User("1", "@vipin", "Vipin Kumar");
      User user2 = new User("2", "@alan", "Alan Turing");
      User user3 = new User("3", "@bhalendu", "Bhalendu Joshi");
      User user4 = new User("4", "@mahesh", "Mahesh Chand");
      Address toAddress = new Address(1l, "New York", "America");
      Address fromAdd = new Address(2l, "Jamshedpur", "India");
      Order  ms = new Order("111","@vipin", toAddress, fromAdd, "200 USD");
      Order  ms2 = new Order("112","@vipin", toAddress, fromAdd, "400 USD");
      List<Order> orders = new ArrayList<>();
      orders.add(ms);
      orders.add(ms2);
      orderList.add(ms);
      orderList.add(ms2);
      user1.setOrders(orders); 
      orderListStore.put(user1.getId(), orders);
      orderListStore.put(user2.getId(), orders);
      orderListStore.put(user3.getId(), orders);
      orderListStore.put(user4.getId(), orders);
      userStoreList.add(user1);
      userStoreList.add(user2);
      userStoreList.add(user3);
      userStoreList.add(user4);
  }
  
  public static List<User> loadUser() {
	  return userStoreList;
  }
  
  public static List<Order> loadOrderList() {
	  return orderList;
  }
  
  public static List<Order> findOrderByUserId(String userId) {
	  return orderListStore.get(userId);
  }
}
