package pl.bets365mj.bet;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bets365mj.fixture.Fixture;

import java.util.List;

public interface BetRepository extends JpaRepository<Bet, Integer> {

    List<Bet> findAllByCouponId(int id);
    Bet getById(Integer id);
    List<Bet> findAllByEventMatchday(int id);
    List<Bet> findAllByEvent(Fixture fixture);
}
