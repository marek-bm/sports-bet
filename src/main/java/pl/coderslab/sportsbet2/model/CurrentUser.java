package pl.coderslab.sportsbet2.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {
    private final pl.coderslab.sportsbet2.model.User user;

    public CurrentUser(String username,
                       String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.coderslab.sportsbet2.model.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public pl.coderslab.sportsbet2.model.User getUser(){
        return user;
    }
}
