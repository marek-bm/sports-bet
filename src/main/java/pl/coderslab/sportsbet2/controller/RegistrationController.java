package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.sportsbet2.model.Country;
import pl.coderslab.sportsbet2.model.DTO.UserDTO;
import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.model.Wallet;
import pl.coderslab.sportsbet2.service.CountryService;
import pl.coderslab.sportsbet2.service.UserService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registrationInit(Model model){

        List<Country> countries=countryService.findAll();

        UserDTO userDTO=new UserDTO();

        model.addAttribute("userDTO", userDTO);
        model.addAttribute("countries", countries);
        return "registration";

    }


    @RequestMapping (value = "/register", method = RequestMethod.POST)
    public String registrationFinish(@Valid UserDTO userDTO, BindingResult result, Model model){

        if (result.hasErrors()){
            return "registration";
        }

        User user=userDTO.getUser();
        String userName=user.getUsername();

        //verification if username exists in DB
        if(userExists(userName)==true){
            return "registration";
        }



        //verification if email address exists in DB
        //alternative way of searching by stream
        if (userService.findAll().stream().anyMatch(u -> u.getMail().equals(user.getMail()))) {
            return "registration";
        }


        Wallet wallet=userDTO.getWallet();
        wallet.setBalance(BigDecimal.valueOf(0));
        user.setWallet(wallet);
        userService.saveUser(user);

        return "success";

    }

    //private method to check verify if username given in form is free to use
    private boolean userExists(String userName){
        if(userService.findByUserName(userName)==null){
            return false;
        }
        else return true;
    }
}
