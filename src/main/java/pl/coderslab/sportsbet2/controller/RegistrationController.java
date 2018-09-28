package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.sportsbet2.model.Country;
import pl.coderslab.sportsbet2.model.DTO.UserDTO;
import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.model.Wallet;
import pl.coderslab.sportsbet2.service.CountryService;
import pl.coderslab.sportsbet2.service.UserService;

import javax.validation.Valid;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @Autowired
    Validator validator;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registrationInit(Model model){



        UserDTO userDTO=new UserDTO();

        model.addAttribute("userDTO", userDTO);


        return "registration";
    }


    @RequestMapping (value = "/register", method = RequestMethod.POST)
    public String registrationFinish(@Valid UserDTO userDTO, BindingResult result, Model model){

        if (result.hasErrors()){
            return "registration";
        }

        User user=new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
        user.setMail(userDTO.getMail());
        user.setPassword(userDTO.getPassword());
        user.setEnabled(true);
        user.setAdult(userDTO.getAdult());
        user.setStreet(userDTO.getStreet());
        user.setCity(userDTO.getCity());
        user.setCountry(userDTO.getCountry());
        user.setDataProcessingAcknowledgement(userDTO.getDataProcessingAcknowledgement());
        user.setWallet(new Wallet());
        user.getWallet().setBankAccount(userDTO.getBankAccount());
        user.getWallet().setBalance(BigDecimal.valueOf(0));
        userService.saveUser(user);

        return "success";

        /* this was first verification before custom validators were introduced

        verification if username exists in DB
        if(userExists(userName)==true){
            return "registration";
        }

        verification if email address exists in DB
        alternative way of searching by stream
        if (userService.findAll().stream().anyMatch(u -> u.getMail().equals(user.getMail()))) {
            return "registration";
        }
         */


    }

    @ModelAttribute
    public void showCountires(Model model){
        List<Country> countries=countryService.findAll();
        model.addAttribute("countries", countries);

    }

    //private method to check verify if username given in form is free to use
    private boolean userExists(String userName){
        if(userService.findByUserName(userName)==null){
            return false;
        }
        else return true;
    }
}
