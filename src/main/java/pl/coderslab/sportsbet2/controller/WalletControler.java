package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportsbet2.model.Bet;
import pl.coderslab.sportsbet2.model.Coupon;
import pl.coderslab.sportsbet2.model.User;
import pl.coderslab.sportsbet2.model.Wallet;
import pl.coderslab.sportsbet2.repository.BetRepository;
import pl.coderslab.sportsbet2.service.CouponService;
import pl.coderslab.sportsbet2.service.UserService;
import pl.coderslab.sportsbet2.service.WalletService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"wallet","sessionBets"})
public class WalletControler {

    @Autowired
    UserService userService;

    @Autowired
    WalletService walletService;

    @Autowired
    CouponService couponService;

    @Autowired
    BetRepository singleBetRepository;

    @ModelAttribute("wallet")
    public Wallet walletInSession(Authentication authentication){

        User current=null;

        try{
            current=userService.findByUserName(authentication.getName());
            Wallet wallet=current.getWallet();
            return wallet;

        }catch (NullPointerException e){ }

        return null;
    }


    @RequestMapping("/wallet")
    public String showUsersWallet(@ModelAttribute("wallet") Wallet wallet){
        return "wallet";
    }


    @RequestMapping(value = "/chargeaccount", method = RequestMethod.GET)
    public String chargeWalletRequest(@ModelAttribute("wallet") Wallet wallet){
        return "wallet-charge";

    }

    @RequestMapping(value = "/chargeaccount", method = RequestMethod.POST)
    public String chargeWalletProcessing(@ModelAttribute("wallet") Wallet wallet, @RequestParam BigDecimal charge ){
        BigDecimal updatedBalance=wallet.getBalance().add(charge);
        wallet.setBalance(updatedBalance);
        walletService.saveWallet(wallet);
        return "redirect:/wallet";
    }

    @RequestMapping(value = "/payfromaccount", method = RequestMethod.POST)
    public String payTheCoupon(@ModelAttribute("wallet") Wallet wallet,
                               @ModelAttribute("sessionBets") List<Bet> sessionBets,
                               @RequestParam BigDecimal charge,
                               Authentication authentication,
                               HttpServletRequest request,
                               Model model){

      if( wallet.getBalance().compareTo(charge) < 0){
          return "moneyalert";
      }
      else {
          wallet.setBalance(wallet.getBalance().subtract(charge));
          HttpSession session = request.getSession();
          saveCoupon(sessionBets, charge, authentication, session);
          model.addAttribute("sessionBets", new ArrayList<>());

          return "redirect:/mycoupons";
      }

    }

    private void saveCoupon(@ModelAttribute("sessionBets") List<Bet> sessionBets, @RequestParam BigDecimal charge, Authentication authentication, HttpSession session) {
        User current=userService.findByUserName(authentication.getName());
        Coupon coupon = new Coupon();
        coupon.setBetValue(charge);
        coupon.setUser(current);

        couponService.save(coupon);

        List<Bet> bets = new ArrayList<>();
        for (Bet bet : sessionBets) {
            bet.setCoupon(coupon);
            singleBetRepository.save(bet);
            bets.add(bet);
        }
        BigDecimal winValue=calculateWinValue(bets,charge);
        coupon.setWinValue(winValue);
        coupon.setBets(bets);
        couponService.save(coupon);

    }


    private static BigDecimal calculateWinValue(List<Bet> bets, BigDecimal charge){

        BigDecimal win=charge;

        for(Bet bet:bets){
            win=win.multiply(bet.getBetPrice());
        }

        return win;
    }

}
