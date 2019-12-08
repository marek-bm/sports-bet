package pl.bets365mj.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bets365mj.coupon.CouponService;
import pl.bets365mj.fixture.FixtureService;
import pl.bets365mj.user.User;
import pl.bets365mj.user.UserService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;

@Controller
@SessionAttributes("coupon")
public class WalletControler {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @RequestMapping("/wallet")
    public String showUsersWallet(Authentication authentication, Model model) {
        if(authentication!=null){
            User current = userService.findByUsername(authentication.getName());
            Wallet wallet = current.getWallet();
            model.addAttribute("wallet", wallet);
        }
        return "wallet";
    }

    @RequestMapping(value = "/chargeaccount", method = RequestMethod.GET)
    public String chargeWalletRequest(Authentication authentication, Model model) {
        User current = userService.findByUsername(authentication.getName());
        Wallet wallet = current.getWallet();
        model.addAttribute("wallet", wallet);
        return "wallet-charge";
    }

    @RequestMapping(value = "/chargeaccount", method = RequestMethod.POST)
    public String chargeWalletProcessing(@Valid Wallet wallet, @RequestParam BigDecimal charge) {
        BigDecimal updatedBalance = wallet.getBalance().add(charge);
        wallet.setBalance(updatedBalance);
        Date date = new Date();
        wallet.getTransactions().add(date + " you charged your account by " + charge + " PLN");
        walletService.save(wallet);
        return "redirect:/wallet";
    }

    @RequestMapping("/withdraw}")
    public String withdrawCash() {
        return "withdraw";
    }

}
