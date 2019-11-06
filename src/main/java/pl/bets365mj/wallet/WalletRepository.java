package pl.bets365mj.wallet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bets365mj.users.User;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findByOwner(User user);
}
