package pl.coderslab.sportsbet2.service;

import pl.coderslab.sportsbet2.model.Coupon;

import java.util.List;

public interface CouponService {

    Coupon save(Coupon coupon);
    List<Coupon> findAllByUser(String userName);
    List<Coupon> findAllByUserUsername(String userName);
    Coupon findById(int id);
}
