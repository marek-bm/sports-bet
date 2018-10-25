package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.Coupon;
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
}
