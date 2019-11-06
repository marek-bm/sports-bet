package pl.bets365mj.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.bets365mj.coupon.CouponService;
import pl.bets365mj.fixture.FixtureService;
import pl.bets365mj.user.User;
import pl.bets365mj.user.UserService;

import java.math.BigDecimal;
import java.util.Date;

@Controller
public class WalletControler {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    CouponService couponService;

    @Autowired
    FixtureService fixtureService;

    @ModelAttribute("wallet")
    public Wallet walletInSession(Authentication authentication) {
        User current = null;
        try {
            current = userService.findByUsername(authentication.getName());
            Wallet wallet = current.getWallet();
            return wallet;

        } catch (NullPointerException e) {
        }
        return null;
    }

    @RequestMapping("/wallet")
    public String showUsersWallet(@ModelAttribute("wallet") Wallet wallet) {
        return "wallet";
    }

    @RequestMapping(value = "/chargeaccount", method = RequestMethod.GET)
    public String chargeWalletRequest(@ModelAttribute("wallet") Wallet wallet) {
        return "wallet-charge";
    }

    @RequestMapping(value = "/chargeaccount", method = RequestMethod.POST)
    public String chargeWalletProcessing(@ModelAttribute("wallet") Wallet wallet, @RequestParam BigDecimal charge) {
        BigDecimal updatedBalance = wallet.getBalance().add(charge);
        wallet.setBalance(updatedBalance);
        Date date = new Date();
        wallet.getTransactions().add(date + " you charged your account by " + charge + " PLN");
        walletService.saveWallet(wallet);
        return "redirect:/wallet";
    }

    @RequestMapping("/withdraw}")
    public String withdrawCash() {
        return "withdraw";
    }

}
