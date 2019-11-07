package pl.bets365mj.appConfig.userDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bets365mj.role.Role;
import pl.bets365mj.user.User;
import pl.bets365mj.user.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user= Optional
                .ofNullable(userService.findByUsername(username))
                .orElseThrow(()-> new UsernameNotFoundException(username));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new CurrentUser(user.getUsername(), user.getPassword(), grantedAuthorities, user);
    }
}
