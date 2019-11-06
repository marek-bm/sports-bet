package pl.bets365mj.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bets365mj.bet.Bet;
import pl.bets365mj.fixture.Fixture;
import pl.coderslab.bets365mj.fixture.*;
import pl.bets365mj.bet.BetService;
import pl.bets365mj.user.User;
import pl.bets365mj.wallet.Wallet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponRepository couponRepository;

    @Autowired
    BetService betService;

    @Override
    public Coupon save(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> findAllByUser(String userName) {
        List<Coupon> coupons = new ArrayList<>();
        try {
            coupons = couponRepository.findAllByUser(userName);
        } catch (Exception e) {
        }
        return coupons;
    }

    @Override
    public List<Coupon> findAllByUserUsername(String userName) {
        return couponRepository.findAllByUserUsername(userName);
    }

    @Override
    public Coupon findById(int id) {
        return couponRepository.findById(id);
    }

    @Override
    public List<Coupon> findAllByBetsIn(List<Bet> bets) {
        return couponRepository.findAllByBetsIn(bets);
    }

    @Override
    public void resolveCoupons(List<Coupon> coupons) {
        for (Coupon coupon : coupons) {
            Boolean active = coupon.isActive();
            Boolean won = coupon.isWon();
            List<Bet> bets = coupon.getBets();

            resolveBets(bets);
            int totalBets = bets.size();
            int activeBets = 0;
            int finishedBets = 0;
            List<Boolean> betStatus = new ArrayList<>();

            for (Bet b : bets) {
                betStatus.add(b.isWon());
                if (b.getEvent().getMatchStatus().equals("active")) {
                    activeBets++;
                } else {
                    finishedBets++;
                }
            }

            if (activeBets == 0) {
                coupon.setActive(false);
            }

            if (betStatus.contains(false)) {
                coupon.setWon(false);
                Wallet wallet = coupon.getUser().getWallet();
            } else {
                coupon.setWon(true);
                Wallet wallet = coupon.getUser().getWallet();
                wallet.setBalance(wallet.getBalance().add(coupon.getBetValue()));
                wallet.getTransactions().add("You won " + coupon.getBetValue() + "PLN on coupon id " + coupon.getId());
            }
            couponRepository.save(coupon);
        }
    }

    public List<Bet> resolveBets(List<Bet> bets) {
        for (Bet bet : bets) {
            Fixture fixture = bet.getEvent();
            String fixtureStatus = fixture.getMatchStatus();

            if (fixture.equals("finished")) {
                String betType = bet.getPlacedBet();
                if (betType.equals("H") || betType.equals("D") || betType.equals("A")) {
                    String finalTimeResul = fixture.getFTR();
                    if (betType.equals(finalTimeResul)) {
                        bet.setWon(true);
                    } else {
                        bet.setWon(false);
                    }
                } else {
                    int goalsInFixture = fixture.getFTAG() + fixture.getFTHG();

                    if (goalsInFixture < 2.5 && betType.equals("LT2_5") || (goalsInFixture > 2.5) && betType.equals("GT2_5")) {
                        bet.setWon(true);
                    } else {
                        bet.setWon(false);
                    }
                }
            }
        }
        return bets;
    }

    public void saveCoupon(Coupon coupon, BigDecimal charge, User user) {
        //saving coupon to generate Id
        couponRepository.save(coupon);
        couponRepository.flush();
        int id = coupon.getId();
        //saving coupon to fill other data
        coupon.setBetValue(charge);
        coupon.setUser(user);
        List<Bet> betsOnCoupon = coupon.getBets();
        BigDecimal winValue = calculateWinValue(betsOnCoupon, charge);
        coupon.setWinValue(winValue);
        couponRepository.save(coupon);

        //saving bets, cascade saving was not warking
        for (Bet b : betsOnCoupon) {
            b.setCoupon(coupon);
            betService.save(b);
        }
    }

    private static BigDecimal calculateWinValue(List<Bet> bets, BigDecimal charge) {
        BigDecimal win = charge;
        for (Bet bet : bets) {
            win = win.multiply(bet.getBetPrice());
        }
        return win;
    }
}

