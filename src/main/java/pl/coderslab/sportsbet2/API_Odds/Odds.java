package pl.coderslab.sportsbet2.API_Odds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.Team;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class Odds {

    @Autowired
    Statistics statistics;

    public Fixture fixtureOdds(Fixture fixture){
        Team home=fixture.getHomeTeam();
        Team away=fixture.getAwayTeam();
        Season season=fixture.getSeason();

        Map<String, Double> probability= matchProbabilityMatirx(home, away, season);


        BigDecimal homeWinOdd= null;
        BigDecimal drawOdd= null;
        BigDecimal awayWinOdd= null;
        BigDecimal goalLess2_5= null;
        BigDecimal goalMore2_5= null;

        try {
            homeWinOdd = BigDecimal.valueOf(1/probability.get("win"));
            drawOdd = BigDecimal.valueOf(1/probability.get("draw"));
            awayWinOdd = BigDecimal.valueOf(1/probability.get("lost"));
            goalLess2_5 = BigDecimal.valueOf(1/probability.get("goalsLess"));
            goalMore2_5 = BigDecimal.valueOf(1/probability.get("goalsMore"));
        } catch (Exception e) {
        }

        fixture.setHomeWinOdd(homeWinOdd);
        fixture.setDrawOdd(drawOdd);
        fixture.setAwayWinOdd(awayWinOdd);
        fixture.setGoal_less_2_5(goalLess2_5);
        fixture.setGoal_more_2_5(goalMore2_5);

        return fixture;
    }



    private Map<String, Double> matchProbabilityMatirx(Team home, Team away, Season season) {
        double homeTeamGoals=statistics.homeTeamGoalsPrediction(home,away,season);
        double[] homeTeamGoalsProbability=statistics.goalsNumberProbability(homeTeamGoals);

        double awayTeamGoals=statistics.awayTeamGoalsPrediction(home,away,season);
        double[] awayTeamGoalsProbability=statistics.goalsNumberProbability(awayTeamGoals);

        double[][] matchResultProbabilityMatix=statistics.matchResultProbability(homeTeamGoalsProbability, awayTeamGoalsProbability);

        Map<String, Double> odds=statistics.odds(matchResultProbabilityMatix);

        return odds;
    }
}
