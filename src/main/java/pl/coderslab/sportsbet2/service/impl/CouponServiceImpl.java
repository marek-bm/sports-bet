package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.Bet;
import pl.coderslab.sportsbet2.model.Coupon;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.Wallet;
import pl.coderslab.sportsbet2.repository.CouponRepository;
import pl.coderslab.sportsbet2.service.CouponService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponRepository betCouponRepository;

    @Override
    public Coupon save(Coupon coupon) {
        return betCouponRepository.save(coupon);
    }

    @Override
    public List<Coupon> findAllByUser(String userName) {

        List<Coupon> coupons=new ArrayList<>();

        try {
            coupons= betCouponRepository.findAllByUser(userName);
        } catch (Exception e) {

        }

        return  coupons;
    }

    @Override
    public List<Coupon> findAllByUserUsername(String userName) {
        return betCouponRepository.findAllByUserUsername(userName);
    }

    @Override
    public Coupon findById(int id) {
        return betCouponRepository.findById(id);
    }

    @Override
    public List<Coupon> findAllByBetsIn(List<Bet> bets) {
        return betCouponRepository.findAllByBetsIn(bets);
    }

    @Override
    public void resolveCoupons(List<Coupon> coupons) {

        for(Coupon coupon:coupons){
            Boolean active=coupon.isActive();
            Boolean won=coupon.isWon();
            List<Bet> bets=coupon.getBets();

            resolveBets(bets);

            int totalBets=bets.size();
            int activeBets=0;
            int finishedBets=0;
            List<Boolean> betStatus=new ArrayList<>();


            for(Bet b:bets){
                betStatus.add(b.isWon());

                if(b.getEvent().getMatchStatus().equals("active")){
                    activeBets++;
                }
                else {
                    finishedBets++;
                }
            }

            if (activeBets==0){
                coupon.setActive(false);
            }

            if(betStatus.contains(false)){
                coupon.setWon(false);
                Wallet wallet=coupon.getUser().getWallet();
            }

            else {
                coupon.setWon(true);
                Wallet wallet=coupon.getUser().getWallet();
                wallet.setBalance(wallet.getBalance().add(coupon.getBetValue()));
                wallet.getTransactions().add("You won " + coupon.getBetValue() + "PLN on coupon id " + coupon.getId());
            }

            betCouponRepository.save(coupon);
        }

    }


    public List<Bet> resolveBets(List<Bet> bets){

        for(Bet bet:bets) {
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

}
