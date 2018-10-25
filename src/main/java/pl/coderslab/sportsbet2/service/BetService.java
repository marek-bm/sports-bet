package pl.coderslab.sportsbet2.service;

import pl.coderslab.sportsbet2.model.Bet;

import java.util.List;


public interface BetService {

    Bet save(Bet bet);
    Bet findById(Integer betId);

    List<Bet> findAllByCouponId(int id);
    List<Bet> findAllByEventMatchday(int id);
}
