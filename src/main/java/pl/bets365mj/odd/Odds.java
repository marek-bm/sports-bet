package pl.bets365mj.odd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.Team;
import pl.bets365mj.fixture.Fixture;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class Odds {

    @Autowired
    MatchStatistics matchStatistics;

    public Fixture fixtureOdds(Fixture fixture) {
        Team homeTeam = fixture.getHomeTeam();
        Team awayTeam = fixture.getAwayTeam();
        Season season = fixture.getSeason();
        Map<String, Double> probability = getScoreProbability(homeTeam, awayTeam, season);
        BigDecimal homeTeamWinOdd = null;
        BigDecimal drawOdd = null;
        BigDecimal awayTeamWinOdd = null;
        BigDecimal goalLess2_5 = null;
        BigDecimal goalMore2_5 = null;

        try {
            homeTeamWinOdd = BigDecimal.valueOf(1 / probability.get("win"));
            drawOdd = BigDecimal.valueOf(1 / probability.get("draw"));
            awayTeamWinOdd = BigDecimal.valueOf(1 / probability.get("lost"));
            goalLess2_5 = BigDecimal.valueOf(1 / probability.get("goalsLess"));
            goalMore2_5 = BigDecimal.valueOf(1 / probability.get("goalsMore"));
        } catch (Exception e) {
        }

        fixture.setHomeWinOdd(homeTeamWinOdd);
        fixture.setDrawOdd(drawOdd);
        fixture.setAwayWinOdd(awayTeamWinOdd);
        fixture.setGoal_less_2_5(goalLess2_5);
        fixture.setGoal_more_2_5(goalMore2_5);
        return fixture;
    }

    private Map<String, Double> getScoreProbability(Team home, Team away, Season season) {
        double homeTeamGoals = matchStatistics.homeTeamGoalsPrediction(home, away, season);
        double[] homeTeamGoalsProbability = matchStatistics.goalsNumberProbability(homeTeamGoals);
        double awayTeamGoals = matchStatistics.awayTeamGoalsPrediction(home, away, season);
        double[] awayTeamGoalsProbability = matchStatistics.goalsNumberProbability(awayTeamGoals);
        double[][] matchResultProbabilityMatix = matchStatistics.matchResultProbability(homeTeamGoalsProbability, awayTeamGoalsProbability);
        Map<String, Double> odds = matchStatistics.odds(matchResultProbabilityMatix);
        return odds;
    }
}