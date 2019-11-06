package pl.coderslab.sportsbet2.users.userDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.users.Role;
import pl.coderslab.sportsbet2.users.User;
import pl.coderslab.sportsbet2.users.UserService;

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
                .ofNullable(userService.findByUserName(username))
                .orElseThrow(()-> new UsernameNotFoundException(username));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new CurrentUser(user.getUsername(), user.getPassword(), grantedAuthorities, user);
    }
}
