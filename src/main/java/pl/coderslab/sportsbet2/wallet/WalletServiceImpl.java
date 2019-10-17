package pl.coderslab.sportsbet2.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.users.User;
import pl.coderslab.sportsbet2.wallet.Wallet;
import pl.coderslab.sportsbet2.wallet.WalletRepository;
import pl.coderslab.sportsbet2.users.UserService;
import pl.coderslab.sportsbet2.wallet.WalletService;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    UserService userService;

    public  void saveWallet(Wallet wallet){
        walletRepository.save(wallet);
    }

    @Override
    public Wallet findByOwner(User user) {
        return walletRepository.findByOwner(user);
    }

    @Override
    public Wallet findByOwner(String userName) {
        User user=userService.findByUserName(userName);
        return walletRepository.findByOwner(user);
    }

}
