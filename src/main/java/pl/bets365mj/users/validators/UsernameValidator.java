package pl.bets365mj.users.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bets365mj.users.User;
import pl.bets365mj.users.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UsernameValidator implements ConstraintValidator<UsernameUnique, String> {
    @Autowired
    UserService userService;

    public void initialize(UsernameUnique constraint) {
    }

    public boolean isValid(String username, ConstraintValidatorContext context) {
        User user = userService.findUsersByUsername(username);
        if (user == null) {
            return true;
        }
        return false;
    }
}
