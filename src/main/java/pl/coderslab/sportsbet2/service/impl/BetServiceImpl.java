package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.sportsbet2.model.Bet;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.repository.BetRepository;
import pl.coderslab.sportsbet2.service.BetService;
import pl.coderslab.sportsbet2.service.FixtureService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BetServiceImpl implements BetService {

    @Autowired
    BetRepository betRepository;

    @Override
    public Bet save(Bet bet) {
       return betRepository.save(bet);
    }

    @Override
    public Bet findById(Integer betId) {
        return betRepository.getById(betId);
    }

    @Override
    public List<Bet> findAllByCouponId(int id) {
        return betRepository.findAllByCouponId(id);
    }

    @Override
    public List<Bet> findAllByEventMatchday(int id) {
        return betRepository.findAllByEventMatchday(id);
    }

    public static Bet getBet(@RequestParam Integer event, @RequestParam BigDecimal betPrice, @RequestParam String placedBet, FixtureService fixtureService) {
        Bet bet=new Bet();
        Fixture fixture=fixtureService.findById(event);
        bet.setEvent(fixture);
        bet.setBetPrice(betPrice);
        bet.setPlacedBet(placedBet);

        return bet;
    }



}
