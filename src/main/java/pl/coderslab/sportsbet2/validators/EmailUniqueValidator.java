package pl.coderslab.sportsbet2.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.sportsbet2.users.User;
import pl.coderslab.sportsbet2.users.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {
   @Autowired
    UserService userService;

    public void initialize(EmailUnique constraint) {
   }

   public boolean isValid(String email, ConstraintValidatorContext context) {
        User user=userService.findUserByMail(email);
        if (user==null){
            return true;
        }
        return false;
   }
}
