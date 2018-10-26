package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {



    @Autowired
    UserDetailsService userDetailsService;


//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String loginRequest(Model model) {
//        return "login";
//    }

}
