package pl.bets365mj.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.bets365mj.batchDataLoader.FixtureProcessor;
import pl.bets365mj.bet.Bet;
import pl.bets365mj.bet.BetService;
import pl.bets365mj.coupon.Coupon;
import pl.bets365mj.coupon.CouponService;
import pl.bets365mj.fixtureMisc.*;
import pl.bets365mj.odd.FootballOdd;
import pl.bets365mj.fixtureMisc.LeagueRepository;
import pl.bets365mj.fixtureMisc.SeasonServiceImpl;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class FixtureController {

    @Autowired
    SportCategoryService sportCategoryService;

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    FixtureService fixtureService;

    @Autowired
    SeasonServiceImpl seasonService;

    @Autowired
    TeamService teamService;

    @Autowired
    FixtureProcessor fixtureProcessor;

    @Autowired
    FootballOdd footballOdd;

    @Autowired
    BetService betService;

    @Autowired
    CouponService couponService;

    @RequestMapping("/fixture-finished")
    public String showFinishedFixtures(Model model) {
        Season currentSeason = seasonService.findById(7);
        List<Fixture> currentSeasonGames = fixtureService.findAllBySeasonAndMatchStatus(currentSeason, "finished");
        Map<Integer, List<Fixture>> fixtureMap = fixtureService.fixturesAsMapSortByMatchday(currentSeasonGames);
        model.addAttribute("fixtures", fixtureMap);
        return "results-all";
    }

    @RequestMapping("/active")
    public String showActiveFixtures(Model model) {
        List<Fixture> activeEvents = fixtureService.findAllByMatchStatus("active");
        Map<Integer, List<Fixture>> fixtureMap = fixtureService.fixturesAsMapSortByMatchday(activeEvents);
        model.addAttribute("activeFixtures", fixtureMap);
        Bet bet = new Bet();
        model.addAttribute("bet", bet);
        return "results-active";
    }

    @RequestMapping(value = "/fixture-edit/{id}", method = RequestMethod.GET)
    public String editFixture(@PathVariable Integer id, Model model) {
        Fixture fixture = fixtureService.findById(id);
        model.addAttribute("fixture", fixture);
        return "forms/fixture-edit";
    }

    @RequestMapping(value = "/fixture-edit", method = RequestMethod.POST)
    public String saveEditedFixture(@Valid Fixture fixture, BindingResult result) {
        if (result.hasErrors()) {
            return "forms/fixture-edit";
        }
        fixtureService.saveFixture(fixture);
        FixtureProcessor.resultSolver(fixture);
        betService.updateBets(fixture);

        List<Bet> bets = betService.findAllByFixture(fixture);
        List<Coupon> coupons = couponService.findAllByBetsIn(bets);
        couponService.resolveCoupons(coupons);
        return "redirect:/active";
    }

    @RequestMapping(value = "/new-fixture", method = RequestMethod.GET)
    public String newFixtureStep1(Model model) {
        Fixture fixture = new Fixture();
        model.addAttribute("fixture", fixture);
        return "forms/fixture-new";
    }


    @RequestMapping(value = "/fixture-new", method = RequestMethod.POST)
    public String newFixtureStep2(@Valid Fixture fixture, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "forms/fixture-new";
        }
        footballOdd.setOdds(fixture);
        fixtureService.saveFixture(fixture);
        return "redirect:/active";
    }

    @ModelAttribute
    public void itemsForFixtureEditionForm(Model model) {
        List<SportCategory> sports = sportCategoryService.findAll();
        model.addAttribute("sports", sports);
        List<League> leagues = leagueRepository.findAll();
        model.addAttribute("leagues", leagues);
        Season season = seasonService.findById(7);
        List<Season> seasons = new ArrayList<>();
        seasons.add(season);
        model.addAttribute("seasons", seasons);
        List<Team> teams = teamService.findAll();
        model.addAttribute("teams", teams);
    }
}
