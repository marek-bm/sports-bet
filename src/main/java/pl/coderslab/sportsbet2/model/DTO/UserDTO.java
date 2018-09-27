package pl.coderslab.sportsbet2.model.DTO;

import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.model.Wallet;

public class UserDTO {
    private User user;
    private Wallet wallet;

    public UserDTO() {
        this.user = new User();
        this.wallet=new Wallet();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }
}
