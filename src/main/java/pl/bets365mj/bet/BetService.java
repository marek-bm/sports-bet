package pl.bets365mj.bet;

import pl.bets365mj.fixture.Fixture;

import java.util.List;

public interface BetService {

    Bet save(Bet bet);
    Bet findById(Integer betId);
    List<Bet> findAllByCouponId(int id);
    List<Bet> findAllByFixtureMatchday(int id);
    List<Bet> findAllByFixture(Fixture fixture);
    List<Bet> updateBets(Fixture fixture);
}
