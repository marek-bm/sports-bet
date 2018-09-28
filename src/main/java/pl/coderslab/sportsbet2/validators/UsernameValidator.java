package pl.coderslab.sportsbet2.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UsernameValidator implements ConstraintValidator<UsernameUnique, String> {
    @Autowired
    UserService userService;

   public void initialize(UsernameUnique constraint) {
   }

   public boolean isValid(String username, ConstraintValidatorContext context) {

       User user=userService.findUsersByUsername(username);
       if (user==null){
           return true;
       }

      return false;
   }
}
