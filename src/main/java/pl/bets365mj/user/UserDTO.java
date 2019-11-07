package pl.bets365mj.user;


import lombok.Data;
import pl.bets365mj.fixtureMisc.Country;
import pl.bets365mj.user.validators.EmailUnique;
import pl.bets365mj.user.validators.UsernameUnique;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {
    @UsernameUnique
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private boolean adult;

    @EmailUnique
    @Email
    private String mail;

    private String bankAccount;

    private String street;

    private String city;

    private String zip;

    private Country country;

    private boolean dataProcessingAcknowledgement = false;

    public boolean isAdult() {
        return adult;
    }

    public boolean allowsDataProcessing() {
        return dataProcessingAcknowledgement;
    }
}
