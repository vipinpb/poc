package hcl.graphql.services.gqlfederation.tracktransfer;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcl.graphql.services.rest.MonolithicHttpClient;

@Service
public class OrderService {

	private List<Order> moneySentList =null;
	@Autowired
	private MonolithicHttpClient monolithicHttpClient;

	@PostConstruct
	public void init() {
		
		moneySentList= monolithicHttpClient.getOrder();
		if (moneySentList == null || moneySentList.size() <= 0) {
			moneySentList = DataHelper.loadMoneySentList();
		}
		
	}

	@NotNull
	public Order lookupMoneySent(@NotNull String id) {
		return moneySentList.stream().filter(m -> m.getId().equals(id)).findAny().get();
	}

	public List<Order> findMoneySentByUserId(String userId) {

		return moneySentList;
	//return DataHelper.findMoneySentByUserId(userId);
	}

	public Address findToaddressByMoneySentId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Address findFromaddressByMoneySentId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}