package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.sportsbet2.model.Country;
import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.service.CountryService;
import pl.coderslab.sportsbet2.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CountryService countryService;


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


    @RequestMapping ("/forgot-password")
    public String forgottenPassword(){
        return "forgot-password";
    }


    @RequestMapping ("/user-edit/{id}")
    public String editUser(@PathVariable int id, Model model){
        User user=userService.findById(id);
        model.addAttribute("user", user);
        return "forms/edit-user";
    }


    @RequestMapping("/user-edit")
    public  String editUser( @Valid User user, BindingResult result){
        if(result.hasErrors()){
            return "forms/edit-user";
        }

        userService.saveUser(user);
        return "user-account";
    }

    @ModelAttribute
    public void showCountires(Model model){
        List<Country> countries=countryService.findAll();
        model.addAttribute("countries", countries);

    }


}
