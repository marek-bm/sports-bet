package pl.bets365mj.bet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/bet-new")
    public String addBetToCoupon(@Valid Bet bet, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "redirect:/active";
        }
        Coupon coupon = (Coupon) session.getAttribute("coupon");
        List<Bet> betsInSession = coupon.getBets();
        if (bet.checkIfUniqe(betsInSession) == false) {
            return "redirect:/home";
        }
        betsInSession.add(bet);
        return "redirect:/home";
    }

    @PostMapping("/delbet")
    public String deleteBetFromCoupon(@RequestParam Integer eventId, HttpSession session) {
        Coupon coupon = (Coupon) session.getAttribute("coupon");
        List<Bet> sessionBets = coupon.getBets();
        removeBetFromSession(eventId, sessionBets);
        return "redirect:/home";
    }

    private void removeBetFromSession(Integer eventId, List<Bet> sessionBets) {
        Iterator<Bet> iterator = sessionBets.listIterator();
        while (iterator.hasNext()) {
            int idFromIterator = iterator.next().getFixture().getId();
            if (idFromIterator == eventId) {
                iterator.remove();
            }
        }
    }

    public static boolean checkIfBetsAreActive(List<Bet> sessionBets) {
        Iterator<Bet> iterator = sessionBets.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getFixture().getMatchStatus().equals("finished"))
                return false;
        }
        return true;
    }
}
