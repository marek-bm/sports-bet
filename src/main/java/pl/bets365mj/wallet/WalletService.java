package pl.bets365mj.wallet;

import pl.bets365mj.user.User;

public interface WalletService {

    void saveWallet(Wallet wallet);
    Wallet findByOwner(User user);
    Wallet findByOwner(String userName);
}
