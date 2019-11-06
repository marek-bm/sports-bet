package pl.coderslab.sportsbet2.coupon;

import pl.coderslab.sportsbet2.bet.Bet;
import pl.coderslab.sportsbet2.users.User;

import java.math.BigDecimal;
import java.util.List;

public interface CouponService {

    Coupon save(Coupon coupon);
    List<Coupon> findAllByUser(String userName);
    List<Coupon> findAllByUserUsername(String userName);
    Coupon findById(int id);
    List<Coupon> findAllByBetsIn(List<Bet> bets);
    void resolveCoupons(List<Coupon> coupons);
    void saveCoupon(Coupon coupon, BigDecimal charge, User user);


}
