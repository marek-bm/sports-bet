package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.CurrentUser;
import pl.coderslab.sportsbet2.model.Role;
import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetaisService implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : user.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new CurrentUser(user.getUsername(), user.getPassword(), grantedAuthorities, user);
    }
}
