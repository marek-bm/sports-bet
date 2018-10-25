package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.coderslab.sportsbet2.model.Bet;
import pl.coderslab.sportsbet2.model.Coupon;
import pl.coderslab.sportsbet2.repository.BetRepository;
import pl.coderslab.sportsbet2.service.CouponService;
import pl.coderslab.sportsbet2.service.FixtureService;

import java.util.List;

@Controller
@SessionAttributes("sessionBets")
public class CouponController {

    @Autowired
    CouponService couponService;

    @Autowired
    FixtureService fixtureService;

    @Autowired
    BetRepository betRepository;

    protected List<Bet> sessionBets;

    @RequestMapping("/mycoupon")
    public String showMyCoupon() {

        return "coupon";
    }


    @RequestMapping("/mycoupons")
    public String myBets(Model model, Authentication authentication){

        String userName= null;
        try {
            userName = authentication.getName();
            List<Coupon> coupons=couponService.findAllByUserUsername(userName);
            model.addAttribute("coupons", coupons); ;
        } catch (NullPointerException e) {

        }

        return "mybets";
    }


    @RequestMapping("/coupondetails")
    public  String showCouponDetails(@RequestParam Integer id, Model model){

        List<Bet> bets=betRepository.findAllByCouponId(id);

        model.addAttribute("bets", bets);

        return "coupon-details";

    }



}


