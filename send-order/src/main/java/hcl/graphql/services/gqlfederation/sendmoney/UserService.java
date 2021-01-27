package hcl.graphql.services.gqlfederation.sendmoney;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private List<User> users = null;

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