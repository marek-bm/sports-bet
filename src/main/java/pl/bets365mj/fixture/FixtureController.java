package pl.bets365mj.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.bets365mj.api.ApiDetails;
import pl.bets365mj.api.MatchDto;
import pl.bets365mj.api.ScoreDto;
import pl.bets365mj.api.TeamDto;
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
        Season currentSeason = seasonService.findById(7);
        List<Fixture> currentSeasonGames = fixtureService.findAllBySeasonAndMatchStatus(currentSeason, "finished");
        Map<Integer, List<Fixture>> fixtureMap = fixtureService.fixturesAsMapSortByMatchday(currentSeasonGames);
        model.addAttribute("fixtures", fixtureMap);
        return "results-all";
    }

    @RequestMapping("/fixture-active")
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
        footballOdd.setOdds(fixture);
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

    @RequestMapping("/api-import")
    @ResponseBody
    public String importFixturesFromApi(){
        Season season=seasonService.findCurrent();
        int currentMatchdayDataBase=season.getCurrentMatchday();
        int requestedMatchday=currentMatchdayDataBase+1;


//        String URL= "http://api.football-data.org/v2/competitions/PL/matches?matchday="+requestedMatchday;
        String URL= ApiDetails.URL_MATCHES+"?matchday=1";
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.set(ApiDetails.TOKEN, ApiDetails.TOKEN_KEY);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity=new HttpEntity<>("parameters", httpHeaders);
        ResponseEntity<FixtureDTO> responseEntity=restTemplate.exchange(URL, HttpMethod.GET, httpEntity, FixtureDTO.class);
        FixtureDTO dto=responseEntity.getBody();
        HttpStatus httpStatus=responseEntity.getStatusCode();
        HttpHeaders responseHeader=responseEntity.getHeaders();
        int availableRequests= Integer.parseInt(responseEntity.getHeaders().get("X-Requests-Available-Minute").get(0));
        int currentMatchdayApi= Integer.parseInt(dto.getMatches().get(0).getSeason().get("currentMatchday"));

//        while (currentMatchdayDataBase!=currentMatchdayApi){
//            if(availableRequests==0){
//                wait60seconds();
//            }
//        }
        List<MatchDto> matches=dto.getMatches();
        MatchDto m=matches.get(0);
        long apiId=m.getApiMatchId();
        Map seasonApi=m.getSeason();
        int seasonId= (int) seasonApi.get("id");
        Date matchDate=m.getUtcDate();
        String status=m.getStatus();
        int matchday=m.getMatchday();
        ScoreDto score=m.getScore();

        TeamDto homeTeamDto=m.getHomeTeam();
        TeamDto awayTeamDto=m.getAwayTeam();

        Fixture fixture=new Fixture();
        fixture.setSeason(season);
        fixture.setMatchday(matchday);

        League league=leagueRepository.findLeagueById(1);
        fixture.setLeague(league);

        Team homeTeam=teamService.findByApiId(homeTeamDto.getApiTeamId());
        fixture.setHomeTeam(homeTeam);

        Team awayTeam=teamService.findByApiId(awayTeamDto.getApiTeamId());
        fixture.setAwayTeam(awayTeam);
        fixture.setDate(matchDate);
        fixture.setMatchStatus(status);

        //ToDo
        // set results to fixture

        return dto.getMatches().get(0).toString();
    }

    private void wait60seconds() {
        try {
            wait(6000);
            System.out.println("Waiting 60 seconds for new requestes pool");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
