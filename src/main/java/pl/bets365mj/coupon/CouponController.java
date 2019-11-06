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
import pl.bets365mj.users.User;
import pl.bets365mj.wallet.Wallet;
import pl.bets365mj.bet.BetRepository;
import pl.bets365mj.fixture.FixtureService;
import pl.bets365mj.users.UserService;
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

    @RequestMapping(value = "/make-bet", method = RequestMethod.POST)
    public String payTheCoupon(@RequestParam(name = "charge") BigDecimal charge,
                               HttpSession session,
                               Authentication authentication,
                               Model model) {

        if (authentication == null) {
            model.addAttribute("error", "You have to login");
            return "error/login-required";
        } else {
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);
            Wallet wallet = user.getWallet();

            if (wallet.getBalance().compareTo(charge) < 0) {
                return "error/moneyalert";
            } else {
                wallet.setBalance(wallet.getBalance().subtract(charge));
                Coupon coupon = (Coupon) session.getAttribute("coupon");
                couponService.saveCoupon(coupon, charge, user);
                wallet.getTransactions().add(new Date() + " you placed  " + charge + " PLN on betting coupon");
                userService.saveUser(user);
                walletService.saveWallet(wallet);
                model.addAttribute("coupon", new Coupon());
            }
        }
        return "redirect:/mycoupons";
    }
}
