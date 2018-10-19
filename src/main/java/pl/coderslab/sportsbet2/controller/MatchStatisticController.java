package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.sportsbet2.API_Odds.Statistics;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.Team;
import pl.coderslab.sportsbet2.repository.FixtureRepository;
import pl.coderslab.sportsbet2.service.SeasonService;

import java.util.Map;

@Controller
public class MatchStatisticController {

    @Autowired
    Statistics statistics;

    @Autowired
    FixtureRepository fixtureRepository;

    @Autowired
    SeasonService seasonService;


    @RequestMapping("/fixture-stats/{id}")
    public String odds(Model model, @PathVariable int id){
        Fixture fixture=fixtureRepository.findOne(id);
        Season season=fixture.getSeason();
        Team home=fixture.getHomeTeam();
        Team away=fixture.getAwayTeam();

        double homeTeamGoals=statistics.homeTeamGoalsPrediction(home,away,season);
        double[] homeTeamGoalsProbability=statistics.goalsNumberProbability(homeTeamGoals);

        double awayTeamGoals=statistics.awayTeamGoalsPrediction(home,away,season);
        double[] awayTeamGoalsProbability=statistics.goalsNumberProbability(awayTeamGoals);

        double[][] matchResultProbabilityMatix=statistics.matchResultProbability(homeTeamGoalsProbability, awayTeamGoalsProbability);

        Map<String, Double> odds=statistics.odds(matchResultProbabilityMatix);

        model.addAttribute("ht", home);
        model.addAttribute("at", away);

        model.addAttribute("hg", homeTeamGoals); //single goal
        model.addAttribute("ag", awayTeamGoals); //single goal

        model.addAttribute("homeGoals", homeTeamGoalsProbability);
        model.addAttribute("awayGoals", awayTeamGoalsProbability);

        model.addAttribute("result", matchResultProbabilityMatix); //match result matrix

        model.addAttribute("odds", odds);

        return "fixture-stats";
    }

}
