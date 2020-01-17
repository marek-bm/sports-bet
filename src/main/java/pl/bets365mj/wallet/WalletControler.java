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

    @GetMapping(value = "/chargeaccount")
    public String chargeWalletRequest(Model model) {
        Wallet wallet = new Wallet();
        model.addAttribute("wallet", wallet);
        return "wallet-charge";
    }

    @PostMapping(value = "/chargeaccount")
    public String chargeWalletProcessing(Authentication authentication, @RequestParam BigDecimal charge) {
        User current = userService.findByUsername(authentication.getName());
        Wallet wallet = current.getWallet();
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
