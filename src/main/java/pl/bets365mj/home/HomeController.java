package pl.bets365mj.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bets365mj.bet.Bet;
import pl.bets365mj.coupon.Coupon;
import pl.bets365mj.fixture.Fixture;
import pl.bets365mj.fixture.FixtureService;
import pl.bets365mj.fixtureMisc.SeasonResultsService;
import pl.bets365mj.fixtureMisc.SeasonService;

import java.util.List;
import java.util.Map;


@Controller
@SessionAttributes("coupon")
public class HomeController {

    @Autowired
    FixtureService fixtureService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    SeasonResultsService seasonResultsService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("username", getUsername());
        model.addAttribute("activeFixtures", activeFixtures());
        model.addAttribute("bet", new Bet());
        return "home";
    }

    @ModelAttribute("coupon")
    public Coupon betsInSession(Model model) {
        Coupon coupon = new Coupon();
        return coupon;
    }

    private String getUsername() {
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        return userName;
    }

//    @ModelAttribute("fixturesActive")
    private Map<Integer, List<Fixture>> activeFixtures() {
        List<Fixture> activeEvents = fixtureService.findAllByMatchStatus("active");
        Map<Integer, List<Fixture>> activeFixtureMap = fixtureService.fixturesAsMapSortByMatchday(activeEvents);
        return activeFixtureMap;
    }

    @ModelAttribute("bet")
    public Bet createEmptyBet(Model model) {
        Bet bet = new Bet();
        model.addAttribute("bet", bet);
        return bet;
    }
}