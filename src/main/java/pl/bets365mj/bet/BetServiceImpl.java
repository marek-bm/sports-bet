package pl.bets365mj.bet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bets365mj.fixture.Fixture;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
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
    public List<Bet> findAllByFixtureMatchday(int id) {
        return betRepository.findAllByFixtureMatchday(id);
    }

    @Override
    public List<Bet> findAllByFixture(Fixture fixture) {
        return betRepository.findAllByFixture(fixture);
    }

    @Override
    public List<Bet> updateBets(Fixture fixture) {
        List<Bet> bets = new ArrayList<>();
        try {
            bets = betRepository.findAllByFixture(fixture);
            resolveBets(bets, fixture);
            betRepository.saveAll(bets);
        } catch (BetNotFoundException e) {
            e.printStackTrace();
        }
        return bets;
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
}
