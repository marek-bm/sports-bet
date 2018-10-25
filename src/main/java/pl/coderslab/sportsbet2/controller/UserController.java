package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.service.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/account")
    public String userAccountDetails(Model model, Authentication authentication){


        User currentUser= null;
        try {
            currentUser = userService.findUsersByUsername(authentication.getName());
            model.addAttribute("user", currentUser);
        } catch (NullPointerException e) {

            if (currentUser==null){
                String warning="Only logged users can assess account panel";
                model.addAttribute("text", warning);
            }
        }

        return "user-account";
    }
}
