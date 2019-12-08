package pl.bets365mj.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.bets365mj.bet.Bet;
import pl.bets365mj.user.User;
import pl.bets365mj.wallet.Wallet;
import pl.bets365mj.bet.BetRepository;
import pl.bets365mj.fixture.FixtureService;
import pl.bets365mj.user.UserService;
import pl.bets365mj.wallet.WalletService;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Controller
@SessionAttributes("coupon")
public class CouponController {

    @Autowired
    CouponService couponService;

    @Autowired
    FixtureService fixtureService;

    @Autowired
    BetRepository betRepository;

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @RequestMapping("/mycoupon")
    public String showMyCoupon(Model model) {
        return "coupon";
    }

    @RequestMapping("/mycoupons")
    public String myBets(Model model, Authentication authentication) {
        String userName = null;
        try {
            userName = authentication.getName();
            List<Coupon> coupons = couponService.findAllByUserUsername(userName);
            model.addAttribute("coupons", coupons);
            ;
        } catch (NullPointerException e) {

        }
        return "mybets";
    }

    @RequestMapping("/coupondetails")
    public String showCouponDetails(@RequestParam Integer id, Model model) {
        List<Bet> bets = betRepository.findAllByCouponId(id);
        model.addAttribute("bets", bets);
        return "coupon-details";
    }

    @RequestMapping(value = "/coupon-finalize", method = RequestMethod.POST)
    public String finalizeCoupon(@RequestParam(name = "charge") BigDecimal charge,
                                 HttpSession session,
                                 Authentication authentication,
                                 Model model) {

        if (authentication == null) {
            model.addAttribute("error", "You have to login");
            return "error/login-required";
        } else {
            User user = getUser(authentication);
            Wallet wallet = user.getWallet();
            if (wallet.getBalance().compareTo(charge) < 0) {
                return "error/moneyalert";
            }
            finalizeCoupon(charge, session, user);
//            updateWallet(charge, wallet);
            walletService.updateWallet(charge, wallet.getId());
            getNewCoupon(model);
        }
        return"redirect:/mycoupons";
    }

    private void finalizeCoupon(@RequestParam(name = "charge") BigDecimal charge, HttpSession session, User user) {
        Coupon coupon = (Coupon) session.getAttribute("coupon");
        couponService.save(coupon, charge, user);
    }

    private void getNewCoupon(Model model) {
        model.addAttribute("coupon", new Coupon());
    }

    private void updateWallet(@RequestParam(name = "charge") BigDecimal charge, Wallet wallet) {
        wallet.setBalance(wallet.getBalance().subtract(charge));
        wallet.getTransactions().add(new Date() + " you placed  " + charge + " PLN on betting coupon");
        walletService.save(wallet);
    }

    private User getUser(Authentication authentication) {
        String userName = authentication.getName();
        return userService.findByUsername(userName);
    }
}
