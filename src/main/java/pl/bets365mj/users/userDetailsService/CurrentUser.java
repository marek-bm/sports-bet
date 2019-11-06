package pl.bets365mj.users.userDetailsService;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class CurrentUser extends User {
    private final pl.bets365mj.users.User user;

    public CurrentUser(String username,
                       String password,
                       Collection<? extends GrantedAuthority> authorities,
                       pl.bets365mj.users.User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public pl.bets365mj.users.User getUser(){
        return user;
    }
}
