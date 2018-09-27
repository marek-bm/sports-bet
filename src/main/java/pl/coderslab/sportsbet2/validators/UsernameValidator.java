package pl.coderslab.sportsbet2.validators;

import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameUnique, String> {
    @Autowired
    UserService userService;

   public void initialize(UsernameUnique constraint) {
   }

   public boolean isValid(String username, ConstraintValidatorContext context) {

       User user=userService.findByUserName(username);
       if (user==null){
           return true;
       }

      return false;
   }
}
