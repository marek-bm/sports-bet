package pl.bets365mj.appConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.bets365mj.user.userDetailsService.CurrentUserDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity //(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CurrentUserDetailsService currentUserDetailsService() {
        return new CurrentUserDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(currentUserDetailsService()).
                passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/fixture-edit/").hasRole("ADMIN")
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/home")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
    }


//    Alternative approach for logging
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication()
//                .usersByUsernameQuery("Select username, password, enabled from users where username = ?")
//                .authoritiesByUsernameQuery("Select u.username, r.role from users u join users_roles ur on u.id=ur.user_id join role r on r.role_id=ur.roles_role_id where u.username=?")
//                .dataSource(dataSource)
//                .passwordEncoder(passwordEncoder());
////                .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN","USER");
//
//    }

}
