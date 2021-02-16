/**
 * 
 */
package hcl.graphql.services.gqlfederation.tracktransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author developer
 *
 */
public class DataHelper {
  static List<Order> moneysentList = new ArrayList<>();
  static Map<String, List<Order>> moneysentListStore = new HashMap<>();
  static List<User> userStoreList = new ArrayList<>();
  static Map<String, MoneyTransfer> transferListStore = new HashMap<>();
  
  static {
      User user1 = new User("1", "@vipin");
      User user2 = new User("2", "@alan");
      User user3 = new User("3", "@bhalendu");
      User user4 = new User("4", "@mahesh");
      Address toAddress = new Address(1l, "New York", "America");
      Address fromAdd = new Address(2l, "Jamshedpur", "India");
      Order  ms = new Order("111", "200 USD");
      Order  ms2 = new Order("112", "400 USD");
      List<Order> moneysents = new ArrayList<>();
      moneysents.add(ms);
      moneysents.add(ms2);
      moneysentList.add(ms);
      moneysentList.add(ms2);
      moneysentListStore.put(user1.getId(), moneysents);
      moneysentListStore.put(user2.getId(), moneysents);
      moneysentListStore.put(user3.getId(), moneysents);
      moneysentListStore.put(user4.getId(), moneysents);
      userStoreList.add(user1);
      userStoreList.add(user2);
      userStoreList.add(user3);
      userStoreList.add(user4);
      MoneyTransfer t = new MoneyTransfer("1234567890", "Transferred successfully");
      t.setOrder(ms);
      t.setUser(user1);
      transferListStore.put("1234567890", t);
  }
  
  public static List<User> loadUser() {
	  return userStoreList;
  }
  
  public static List<Order> loadMoneySentList() {
	  return moneysentList;
  }
  
  public static List<Order> findMoneySentByUserId(String userId) {
	  return moneysentListStore.get(userId);
  }
  
  public static MoneyTransfer findTransferByMtcn(String mtcn) {
	  return transferListStore.get(mtcn);
  }
}
