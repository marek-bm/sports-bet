package pl.coderslab.sportsbet2.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface UsernameUnique {

    String message() default "{Username already exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
