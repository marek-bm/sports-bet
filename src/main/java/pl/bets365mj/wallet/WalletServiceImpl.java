package pl.bets365mj.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bets365mj.user.User;
import pl.bets365mj.user.UserService;

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
