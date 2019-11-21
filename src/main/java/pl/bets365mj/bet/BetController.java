package pl.bets365mj.bet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.bets365mj.coupon.Coupon;
import pl.bets365mj.coupon.CouponService;
import pl.bets365mj.fixture.FixtureService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;

@Controller
public class BetController {

    @Autowired
    BetService betService;

    @Autowired
    FixtureService fixtureService;

    @Autowired
    CouponService couponService;

    @PostMapping("/bet-add")
    public String addBetToCoupon(@Valid Bet bet, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "redirect:/active";
        }
        Coupon coupon = (Coupon) session.getAttribute("coupon");
        List<Bet> betsInSession = coupon.getBets();
        if (bet.isUniqe(betsInSession) == false) {
            return "redirect:/";
        }
        betsInSession.add(bet);
        return "redirect:/";
    }

    @PostMapping("/bet-remove")
    public String deleteBetFromCoupon(@RequestParam Integer eventId, HttpSession session) {
        Coupon coupon = (Coupon) session.getAttribute("coupon");
        List<Bet> sessionBets = coupon.getBets();
        sessionBets.removeIf(x -> x.getFixture().getId().equals(eventId));
        return "redirect:/";
    }
}
