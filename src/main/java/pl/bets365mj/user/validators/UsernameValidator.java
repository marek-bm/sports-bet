package pl.bets365mj.user.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bets365mj.user.User;
import pl.bets365mj.user.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UsernameValidator implements ConstraintValidator<UsernameUnique, String> {
    @Autowired
    UserService userService;

    public void initialize(UsernameUnique constraint) {
    }

    public boolean isValid(String username, ConstraintValidatorContext context) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return true;
        }
        return false;
    }
}
