package pl.bets365mj.user.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bets365mj.user.User;
import pl.bets365mj.user.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
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
