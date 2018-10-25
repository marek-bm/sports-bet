package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.coderslab.sportsbet2.model.Bet;
import pl.coderslab.sportsbet2.service.BetService;
import pl.coderslab.sportsbet2.service.CouponService;
import pl.coderslab.sportsbet2.service.FixtureService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Iterator;
import java.util.List;



@Controller
@SessionAttributes("sessionBets")
public class Bet2Controller {

    @Autowired
    BetService betService;

    @Autowired
    FixtureService fixtureService;

    @Autowired
    CouponService couponService;

    @PostMapping("/bet-new")
    public String addBetToCoupon(@Valid Bet bet, BindingResult result, HttpSession session) {

        if (result.hasErrors()){
            return "redirect:/active";
        }

        List<Bet> betsInSession= (List<Bet>) session.getAttribute("sessionBets");

        if (bet.checkIfUniqe(betsInSession)==false){
            return "redirect:/home";
        }

        betsInSession.add(bet);
        return "redirect:/home";

    }

    @PostMapping("/delbet")
    public String deleteBetFromCoupon(@RequestParam Integer eventId,
                                      @ModelAttribute("sessionBets") List<Bet> sessionBets){

        removeBetFromSession(eventId, sessionBets);

        return "redirect:/home";
    }



    private void removeBetFromSession(Integer eventId, @ModelAttribute("sessionBets") List<Bet> sessionBets) {
        Iterator<Bet> iterator=sessionBets.listIterator();
        while (iterator.hasNext()){
            int idFromIterator= iterator.next().getEvent().getId();
            if (idFromIterator == eventId){
                iterator.remove();
            }
        }
    }

    public static boolean checkIfBetsAreActive(@ModelAttribute("sessionBets") List<Bet> sessionBets){
        Iterator<Bet> iterator=sessionBets.listIterator();
        while (iterator.hasNext()){
            if (iterator.next().getEvent().getMatchStatus().equals("finished"))
                return false;
        }

        return true;
    }

}
