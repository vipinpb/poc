package hcl.graphql.services.gqlfederation.tracktransfer;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();
    private Map<User, Address> userAddressMap = new HashMap<>();

    @PostConstruct
    public void init() {
    	users = DataHelper.loadUser();
    }

    @NotNull
    public User lookupUser(@NotNull String id) {
        User user1 = users.stream().filter(user -> user.getId().equals(id)).findAny().get();
        return user1;
    }

}