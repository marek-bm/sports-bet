package pl.coderslab.sportsbet2.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportsbet2.users.User;
import pl.coderslab.sportsbet2.wallet.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findByOwner(User user);
}
