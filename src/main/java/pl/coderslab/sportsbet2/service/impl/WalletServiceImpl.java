package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.model.Wallet;
import pl.coderslab.sportsbet2.repository.WalletRepository;
import pl.coderslab.sportsbet2.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;

    public  void saveWallet(Wallet wallet){
        walletRepository.save(wallet);
    }

    @Override
    public Wallet findByOwner(User user) {
        return walletRepository.findByOwner(user);
    }
}
