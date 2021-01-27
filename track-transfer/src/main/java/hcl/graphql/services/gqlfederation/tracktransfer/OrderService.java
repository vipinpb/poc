package hcl.graphql.services.gqlfederation.tracktransfer;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private List<Order> moneySentList = new ArrayList<>();
 
    @PostConstruct
    public void init() {
    	moneySentList = DataHelper.loadMoneySentList();
    }

    @NotNull
    public Order lookupMoneySent(@NotNull String id) {
    	Order m1 = moneySentList.stream().filter(m -> m.getId().equals(id)).findAny().get();
        return m1;
    }


	public List<Order> findMoneySentByUserId(String userId) {
		return DataHelper.findMoneySentByUserId(userId);
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