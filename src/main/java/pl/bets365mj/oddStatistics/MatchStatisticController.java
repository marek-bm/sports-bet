package pl.bets365mj.oddStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.SeasonService;
import pl.bets365mj.fixtureMisc.Team;
import pl.bets365mj.fixture.Fixture;
import pl.bets365mj.fixture.FixtureRepository;
import pl.bets365mj.odd.FootballOdd;

import java.math.BigDecimal;
import java.util.Map;

@Controller
public class MatchStatisticController {

    @Autowired
    MatchStatistics matchStatistics;

    @Autowired
    FixtureRepository fixtureRepository;

    @Autowired
    SeasonService seasonService;

    @Autowired
    FootballOdd footballOdd;

    @RequestMapping("/fixture-stats/{id}")
    public String odds(Model model, @PathVariable int id) {
        Fixture fixture = fixtureRepository.findById(id);
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
}
