package pl.coderslab.sportsbet2.service;

import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.model.Wallet;

public interface WalletService {

    void saveWallet(Wallet wallet);
    Wallet findByOwner(User user);
    Wallet findByOwner(String userName);
}
