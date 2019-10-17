package pl.coderslab.sportsbet2.service;

import pl.coderslab.sportsbet2.model.Bet;
import pl.coderslab.sportsbet2.model.Fixture;

import java.util.List;

public interface BetService {

    Bet save(Bet bet);
    Bet findById(Integer betId);
    List<Bet> findAllByCouponId(int id);
    List<Bet> findAllByEventMatchday(int id);
    List<Bet> findAllByEvent(Fixture fixture);
    void updateBets(Fixture fixture);
}
