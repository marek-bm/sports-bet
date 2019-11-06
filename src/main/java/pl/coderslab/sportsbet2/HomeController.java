package pl.coderslab.sportsbet2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportsbet2.bet.Bet;
import pl.coderslab.sportsbet2.coupon.Coupon;
import pl.coderslab.sportsbet2.fixture.Fixture;
import pl.coderslab.sportsbet2.fixture.FixtureService;
import pl.coderslab.sportsbet2.fixtureMisc.SeasonResultsService;
import pl.coderslab.sportsbet2.fixtureMisc.SeasonService;

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

    @GetMapping("/home")
    public String home(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("username", username);
        model.addAttribute("activeFixtures", activeFixtures());
        model.addAttribute("bet");
        return "home";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin() {
        return "admin page";
    }

    @ModelAttribute("coupon")
    public Coupon betsInSession(Model model) {
        Coupon coupon = new Coupon();
        return coupon;
    }

    @ModelAttribute("fixturesActive")
    public Map<Integer, List<Fixture>> activeFixtures() {
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