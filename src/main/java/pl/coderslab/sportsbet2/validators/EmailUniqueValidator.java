package pl.coderslab.sportsbet2.validators;

import org.springframework.beans.factory.annotation.Autowired;
import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {
   @Autowired
    UserService userService;

    public void initialize(EmailUnique constraint) {
   }

   public boolean isValid(String email, ConstraintValidatorContext context) {
        User user=userService.findByMail(email);

        if (user==null){
            return true;
        }
        return false;
   }
}
