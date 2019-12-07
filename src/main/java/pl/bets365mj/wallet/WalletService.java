package pl.bets365mj.wallet;

import org.springframework.web.bind.annotation.RequestParam;
import pl.bets365mj.user.User;

import java.math.BigDecimal;

public interface WalletService {

    void save(Wallet wallet);
    Wallet findByOwner(User user);
    Wallet findByOwner(String userName);
    Wallet updateWallet(BigDecimal charge, Integer walletId);
}
