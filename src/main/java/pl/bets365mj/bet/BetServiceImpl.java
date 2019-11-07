package pl.bets365mj.bet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bets365mj.fixture.Fixture;

import java.util.ArrayList;
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

    @Override
    public List<Bet> findAllByEvent(Fixture fixture) {
        return betRepository.findAllByEvent(fixture);
    }

    @Override
    public void updateBets(Fixture fixture) {
        List<Bet> bets = new ArrayList<>();
        try {
            bets = betRepository.findAllByEvent(fixture);
            resolveBets(bets, fixture);
            betRepository.saveAll(bets);
        } catch (BetNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<Bet> resolveBets(List<Bet> bets, Fixture fixture) {
        for (Bet bet : bets) {
            String userBet = bet.getPlacedBet();
            String actualResult = fixture.getFTR();
            int fixtureGoals = fixture.getFTHG() + fixture.getFTAG();

            try {
                if (userBet.equals("H") || userBet.equals("A") || userBet.equals("D")) {
                    if (userBet.equals(actualResult)) {
                        bet.setWon(true);
                    } else {
                        bet.setWon(false);
                    }
                } else {
                    if (fixtureGoals < 2.5 && userBet.equals("LT2_5")) {
                        bet.setWon(true);
                    } else if (fixtureGoals > 2.5 && userBet.equals("GT2_5")) {
                        bet.setWon(true);
                    } else {
                        bet.setWon(false);
                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return bets;
    }


//    public static Bet getBet(@RequestParam Integer event, @RequestParam BigDecimal betPrice, @RequestParam String placedBet, FixtureService fixtureService) {
//        Bet bet=new Bet();
//        Fixture fixture=fixtureService.findById(event);
//        bet.setEvent(fixture);
//        bet.setBetPrice(betPrice);
//        bet.setPlacedBet(placedBet);
//
//        return bet;
//    }

}
