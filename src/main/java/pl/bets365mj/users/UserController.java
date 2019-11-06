package pl.bets365mj.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.bets365mj.fixtureMisc.Country;
import pl.bets365mj.fixtureMisc.CountryService;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    CountryService countryService;

    @RequestMapping("/account")
    public String userAccountDetails(Model model, Authentication authentication) {
        User currentUser = null;
        try {
            currentUser = userService.findUsersByUsername(authentication.getName());
            model.addAttribute("user", currentUser);
        } catch (NullPointerException e) {
            if (currentUser == null) {
                String warning = "Only logged users can assess account panel";
                model.addAttribute("text", warning);
            }
        }

        return "user-account";
    }

    @RequestMapping("/forgot-password")
    public String forgottenPassword() {
        return "forgot-password";
    }

    @RequestMapping("/user-edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "forms/edit-user";
    }

    @RequestMapping("/user-edit")
    public String editUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "forms/edit-user";
        }
        userService.saveUser(user);
        return "user-account";
    }

    @RequestMapping(value = "/user-updatePassword", method = RequestMethod.GET)
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    public String changeUserPassword(@RequestParam("id") int userId, Model model) {
        model.addAttribute("id", userId);
        return "forms/updatePassword";
    }

    @RequestMapping(value = "/user-updatePassword", method = RequestMethod.POST)
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    public String changeUserPassword(Locale locale,
                                     @RequestParam("password") String password,
                                     @RequestParam("oldpassword") String oldPassword,
                                     Model model) {

        User user = userService.findUsersByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName());

        if (!userService.checkIfValidOldPassword(user, oldPassword)) {
            model.addAttribute("warning", "Invalid password");
            return "forms/updatePassword";
//            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, password);
        return "pass-success";
    }

    @ModelAttribute
    public void showCountires(Model model) {
        List<Country> countries = countryService.findAll();
        model.addAttribute("countries", countries);
    }
}