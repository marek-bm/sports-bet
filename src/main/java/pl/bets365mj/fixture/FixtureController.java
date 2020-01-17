package pl.bets365mj.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.bets365mj.api.ApiDetails;
import pl.bets365mj.api.MatchDto;
import pl.bets365mj.batchDataLoader.FixtureProcessor;
import pl.bets365mj.bet.Bet;
import pl.bets365mj.bet.BetService;
import pl.bets365mj.coupon.Coupon;
import pl.bets365mj.coupon.CouponService;
import pl.bets365mj.fixtureMisc.*;
import pl.bets365mj.odd.FootballOdd;
import pl.bets365mj.fixtureMisc.LeagueRepository;
import pl.bets365mj.fixtureMisc.SeasonServiceImpl;
import pl.bets365mj.oddStatistics.MatchStatistics;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    MatchStatistics matchStatistics;

    @RequestMapping("/fixture-finished")
    public String showFinishedFixtures(Model model) {
        System.out.println("##### FIXTURES FINISHED BEGIN ####");
        Season currentSeason = seasonService.findCurrent();
        System.out.println("currentSeason " + currentSeason);
        List<Fixture> allFixtures = fixtureService.findAllBySeasonAndMatchStatus(currentSeason, "finished");
        System.out.println("Fixtures finished " + allFixtures);
        Map<Integer, List<Fixture>> groupedByMatchday = fixtureService.groupByMatchday(allFixtures);
        System.out.println("Grouped by matchday "+ groupedByMatchday);
        model.addAttribute("fixtures", groupedByMatchday);
        return "results-all";
    }

    @RequestMapping("/fixture-active")
    public String showActiveFixtures(Model model) {
        List<Fixture> activeEvents = fixtureService.findAllByMatchStatus("scheduled");
        Map<String, List<Fixture>> fixtureMap = fixtureService.groupByStatus(activeEvents);
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
        fixtureService.save(fixture);
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
        footballOdd.calculateOdds(fixture);
        fixtureService.save(fixture);
        return "redirect:/active";
    }


    @RequestMapping("/fixture-stats/{id}")
    public String odds(Model model, @PathVariable int id) {
        Fixture fixture = fixtureService.findById(id);
        Season season = fixture.getSeason();
        Team home = fixture.getHomeTeam();
        Team away = fixture.getAwayTeam();

        double homeTeamGoalsConcrete = matchStatistics.homeTeamGoalsPrediction(home, away, season);
        double[] homeTeamGoalsZeroToSix = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(homeTeamGoalsConcrete);
        double awayTeamGoalsConcrete = matchStatistics.awayTeamGoalsPrediction(home, away, season);
        double[] awayTeamGoalsZeroToSix = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(awayTeamGoalsConcrete);
        double[][] matchResultProbabilityMatix = matchStatistics.matchScoreProbabilityMatrix(homeTeamGoalsZeroToSix, awayTeamGoalsZeroToSix);
        Map<String, BigDecimal> odds = footballOdd.getOdds(fixture);

        model.addAttribute("ht", home);
        model.addAttribute("at", away);
        model.addAttribute("hg", homeTeamGoalsConcrete); //single goal
        model.addAttribute("ag", awayTeamGoalsConcrete); //single goal
        model.addAttribute("homeGoals", homeTeamGoalsZeroToSix);
        model.addAttribute("awayGoals", awayTeamGoalsZeroToSix);
        model.addAttribute("result", matchResultProbabilityMatix); //match result matrix
        model.addAttribute("odds", odds);
        return "fixture-stats";
    }

    @RequestMapping("/api-import-next-matchday")
    public String importNextFixturesRoundFromApi(){
        Season season=seasonService.findCurrent();
        int matchdayRequested=season.getCurrentMatchday()+1;
        List<MatchDto> matches = fixtureService.apiGetRequestForFixturesInMatchday(matchdayRequested);

        List<Fixture> fixtures = matches.stream()
                .map(m -> fixtureService.convertDtoToFixtureEntity(m))
                .map(f -> footballOdd.calculateOdds(f))
                .collect(Collectors.toList());

        fixtureService.saveAll(fixtures);
        season.setCurrentMatchday(matchdayRequested);
        seasonService.save(season);
        return "redirect:/";
    }

    @RequestMapping("/api-auto-update")
    public String autoUpdateFixtures(){
        int matchdayApi= fixtureService.getCurrentApiMatchday();
        int matchdayDb = seasonService.findCurrent().getCurrentMatchday();

        if(matchdayApi==matchdayDb){
            List<MatchDto> matches = fixtureService.apiGetRequestForFixturesInMatchday(matchdayApi);
            matches.forEach(m->fixtureService.updateFromDto(m));
        }
        else{
            importNextFixturesRoundFromApi();
        }
        return "redirect:/";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping("/api-update-2018-2019")
    @ResponseBody
    public String apiUpdateSeasonStartingIn2018(){
      String URL ="http://api.football-data.org/v2/competitions/PL/matches?dateFrom=2018-11-06&dateTo=2019-07-01";
//      String URL="http://api.football-data.org/v2/competitions/PL/matches?matchday=11&season=2018";
      ResponseEntity<FixtureRoundDTO> responseEntity=fixtureService.apiGetRequestFixturesRound(URL);
      FixtureRoundDTO dto=responseEntity.getBody();
      List<MatchDto> matches=dto.getMatches();
      List<Fixture> fixtures=matches.stream()
              .map(m->fixtureService.convertDtoToFixtureEntity(m))
              .map(f->footballOdd.calculateOdds(f))
              .collect(Collectors.toList());
      fixtureService.saveAll(fixtures);
      return "OK";
    };

    @ResponseBody
    @RequestMapping("/test")
    public void testTop5HomeTeam(){
        Team team=teamService.findTeamById(14);
        Season season=seasonService.findCurrent();
        List<Fixture> fixtures=fixtureService.findTop5ByAwayTeam(team, season);
        fixtures.forEach(System.out :: println);
    }


    @ModelAttribute
    public void itemsForFixtureEditionForm(Model model) {
        List<SportCategory> sports = sportCategoryService.findAll();
        model.addAttribute("sports", sports);
        List<League> leagues = leagueRepository.findAll();
        model.addAttribute("leagues", leagues);
        Season season = seasonService.findCurrent();
        List<Season> seasons = new ArrayList<>();
        seasons.add(season);
        model.addAttribute("seasons", seasons);
        List<Team> teams = teamService.findAll();
        model.addAttribute("teams", teams);
    }
}
