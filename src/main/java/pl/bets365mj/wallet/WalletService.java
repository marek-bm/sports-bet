package pl.bets365mj.wallet;

import pl.bets365mj.users.User;

public interface WalletService {

    void saveWallet(Wallet wallet);
    Wallet findByOwner(User user);
    Wallet findByOwner(String userName);
}
