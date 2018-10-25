package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {



    @Autowired
    UserDetailsService userDetailsService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginRequest(Model model) {
        return "login";
    }

}
