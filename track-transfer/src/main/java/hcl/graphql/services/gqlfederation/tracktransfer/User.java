package hcl.graphql.services.gqlfederation.tracktransfer;

import java.util.Objects;


public class User {

    private final String id;
    private String username;
   
    public User(final String id) {
        this.id = id;
    }

    public User(final String id, final String username) {
        this.id = id;
        this.username = username;
    }
	public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }
}