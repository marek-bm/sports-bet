package pl.bets365mj.coupon;

import pl.bets365mj.bet.Bet;
import pl.bets365mj.user.User;

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
