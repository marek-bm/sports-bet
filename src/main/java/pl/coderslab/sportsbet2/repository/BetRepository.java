package pl.coderslab.sportsbet2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.sportsbet2.model.Bet;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Integer> {


    List<Bet> findAllByCouponId(int id);
    Bet getById(Integer id);
    List<Bet> findAllByEventMatchday(int id);




}
