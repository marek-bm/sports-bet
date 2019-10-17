package pl.coderslab.sportsbet2.wallet;

import pl.coderslab.sportsbet2.users.User;
import pl.coderslab.sportsbet2.wallet.Wallet;

public interface WalletService {

    void saveWallet(Wallet wallet);
    Wallet findByOwner(User user);
    Wallet findByOwner(String userName);
}
