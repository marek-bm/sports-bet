package pl.coderslab.sportsbet2.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailsService {

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    public boolean findEmailAndUsername(String username, String email) throws UsernameNotFoundException;
}
