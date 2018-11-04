package pl.coderslab.sportsbet2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    Wallet findByOwner(User user);
    Wallet findByOwner(String userName);
}
