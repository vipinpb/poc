package hcl.graphql.services.gqlfederation.sendorder;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hcl.graphql.services.rest.MonolithicHttpClient;

@Service
public class UserService {

    private List<User> users = null;
    
    @Autowired
    private MonolithicHttpClient monolithicHttpClient;

    @PostConstruct
    public void init() {
	   	users = monolithicHttpClient.getUsers();
    	
    	if (users == null || users.size() <= 0) {
    		users = DataHelper.loadUser();
    	}
    }

    @NotNull
    public User lookupUser(@NotNull String id) {
         return users.stream().filter(user -> user.getId().equals(id)).findAny().get();
    }

}