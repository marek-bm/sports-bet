package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderslab.sportsbet2.API_Odds.Odds;
import pl.coderslab.sportsbet2.BatchConverter.FixtureProcessor;
import pl.coderslab.sportsbet2.model.Bet;
import pl.coderslab.sportsbet2.model.Coupon;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.League;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.SportCategory;
import pl.coderslab.sportsbet2.model.sportEvent.Team;
import pl.coderslab.sportsbet2.repository.LeagueRepository;
import pl.coderslab.sportsbet2.service.*;
import pl.coderslab.sportsbet2.service.impl.SeasonServiceImpl;

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
    Odds odds;

    @Autowired
    BetService betService;

    @Autowired
    CouponService couponService;

    @RequestMapping ("/fixture-finished")
    public String resultsDisplay(Model model){

        Season currentSeason=seasonService.findById(7);

        List<Fixture> currentSeasonGames=fixtureService.findAllBySeasonAndMatchStatus(currentSeason, "finished");

        Map<Integer, List<Fixture>> fixtureMap=fixtureService.fixturesAsMapSortByMatchday(currentSeasonGames);

        model.addAttribute("fixtures", fixtureMap);

        return "results-all";
    }


    @RequestMapping("/active")
    public String activeFixtures(Model model){

        List<Fixture> activeEvents=fixtureService.findAllByMatchStatus("active");

        Map<Integer, List<Fixture>> fixtureMap=fixtureService.fixturesAsMapSortByMatchday(activeEvents);

        model.addAttribute("activeFixtures", fixtureMap);

        Bet bet=new Bet();
        model.addAttribute("bet", bet);

        return "results-active";

    }

    @RequestMapping(value = "/fixture-edit/{id}", method = RequestMethod.GET)
    public String editFixture(@PathVariable Integer id, Model model){

        Fixture fixture=fixtureService.findById(id);

        model.addAttribute("fixture", fixture);

        return "forms/fixture-edit";
    }

    @RequestMapping (value = "/fixture-edit", method = RequestMethod.POST)
    public String saveEditedFixture(@Valid Fixture fixture, BindingResult result){

        if (result.hasErrors()){
            return "forms/fixture-edit";
        }

        fixtureService.saveFixture(fixture);

        FixtureProcessor.resultSolver(fixture);
        betService.updateBets(fixture);

        List<Bet> bets=betService.findAllByEvent(fixture);
        List<Coupon> coupons=couponService.findAllByBetsIn(bets);
        couponService.resolveCoupons(coupons);


        return "redirect:/active";
    }


    @RequestMapping(value = "/new-fixture", method = RequestMethod.GET)
    public String newFixtureStep1(Model model){

        Fixture fixture=new Fixture();
        model.addAttribute("fixture", fixture);

        return "forms/fixture-new";
    }


    @RequestMapping(value = "/fixture-new", method = RequestMethod.POST)
    public String newFixtureStep2(@Valid Fixture fixture, BindingResult result, Model model ){

        if (result.hasErrors()){
            return "forms/fixture-new";
        }

        odds.fixtureOdds(fixture);
        fixtureService.saveFixture(fixture);
        return "redirect:/active";
    }



    @ModelAttribute
    public void itemsForFixtureEditionForm(Model model){
        List<SportCategory> sports=sportCategoryService.findAll();
        model.addAttribute("sports", sports);

        List<League> leagues=leagueRepository.findAll();
        model.addAttribute("leagues", leagues);

        Season season=seasonService.findById(7);
        List<Season> seasons=new ArrayList<>();
        seasons.add(season);
        model.addAttribute("seasons", seasons);

        List<Team> teams=teamService.findAll();
        model.addAttribute("teams", teams);
    }

}
