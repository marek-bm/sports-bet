package pl.bets365mj.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.bets365mj.fixtureMisc.Country;
import pl.bets365mj.fixtureMisc.CountryService;
import pl.bets365mj.user.UserDTO;
import pl.bets365mj.user.User;
import pl.bets365mj.wallet.Wallet;
import pl.bets365mj.wallet.WalletRepository;
//import pl.bets365mj.user.UserService;
import pl.bets365mj.user.UserRepository;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class RegistrationController {
    @Autowired
    CountryService countryService;

    @Autowired
    //UserService userService;
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registrationInit(Model model) {
        UserDTO userDTO = new UserDTO();
        model.addAttribute("userDTO", userDTO);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registrationFinish(@Valid UserDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        User user = new User();
//        user.setFirstName(userDTO.getFirstName());
//        user.setLastName(userDTO.getLastName());
        user.setUsername(userDTO.getUsername());
//        user.setMail(userDTO.getMail());
        user.setPassword(userDTO.getPassword());
        user.setEnabled(true);
//        user.setAdult(userDTO.isAdult());
//        user.setStreet(userDTO.getStreet());
//        user.setCity(userDTO.getCity());
//        user.setCountry(userDTO.getCountry());
//        user.setDataProcessingAcknowledgement(userDTO.allowsDataProcessing());
        user.setWallet(new Wallet());
        user.getWallet().setBankAccount(userDTO.getBankAccount());
        user.getWallet().setBalance(BigDecimal.valueOf(0));
        userRepository.save(user);

        Wallet wallet = user.getWallet();
        wallet.setOwner(user);
        walletRepository.save(wallet);
        return "redirect:/login";

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
    public void showCountires(Model model) {
        List<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);

    }
}
