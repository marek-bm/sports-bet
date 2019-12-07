package pl.bets365mj.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bets365mj.user.User;
import pl.bets365mj.user.UserService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserService userService;

    public  void save(Wallet wallet){
        walletRepository.save(wallet);
    }

    @Override
    public Wallet findByOwner(User user) {
        return walletRepository.findByOwner(user);
    }

    @Override
    public Wallet findByOwner(String userName) {
        User user=userService.findByUsername(userName);
        return walletRepository.findByOwner(user);
    }

    @Override
    public Wallet updateWallet(BigDecimal charge, Integer walletId) {
        Optional<Wallet> walletOptional=walletRepository.findById(walletId);
        Wallet wallet=walletOptional.get();
        wallet.setBalance(wallet.getBalance().subtract(charge));
        wallet.getTransactions().add(new Date() + " you placed  " + charge + " PLN on betting coupon");
        save(wallet);
        return wallet;
    }
}
