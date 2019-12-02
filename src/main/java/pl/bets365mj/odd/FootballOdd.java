package pl.bets365mj.odd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.Team;
import pl.bets365mj.fixture.Fixture;
import pl.bets365mj.oddStatistics.MatchStatistics;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class FootballOdd implements Odd {

    @Override
    public BigDecimal calculate(double probability) {
        return BigDecimal.valueOf(1/probability);
    }

    @Autowired
    MatchStatistics matchStatistics;

    public Fixture calculateOdds(Fixture fixture) {
        Team homeTeam = fixture.getHomeTeam();
        Team awayTeam = fixture.getAwayTeam();
        Season season = fixture.getSeason();
        double[][] probabilityMatrix=getProbabilityMatrix(homeTeam,awayTeam,season);

        BigDecimal homeTeamWinOdd= calculateHomeTeamWinOdd(probabilityMatrix);
        BigDecimal drawOdd= calculateDrawOdd(probabilityMatrix);
        BigDecimal awayTeamWinOdd = calculateAwayTeamWinOdds(probabilityMatrix);
        BigDecimal goalsLessEquals2odd= calcualteGoalsLessEquals2odds(probabilityMatrix);
        BigDecimal goalsMoreThan2odd=calculateGoalsMoreThan2odd(probabilityMatrix);

        fixture.setHomeWinOdd(homeTeamWinOdd);
        fixture.setDrawOdd(drawOdd);
        fixture.setAwayWinOdd(awayTeamWinOdd);
        fixture.setGoalsMoreThan2odd(goalsLessEquals2odd);
        fixture.setGoalsLessOrEquals2odd(goalsMoreThan2odd);
        return fixture;
    }

    public Map<String, BigDecimal> getOdds(Fixture fixture){
        Map<String, BigDecimal> odds=new HashMap<>();
        odds.put("win", fixture.getHomeWinOdd());
        odds.put("draw", fixture.getDrawOdd());
        odds.put("lost", fixture.getAwayWinOdd());
        return odds;
    }

    private BigDecimal calculateGoalsMoreThan2odd(double[][] probabilityMatrix) {
        double goalsMoreThan2Probability=matchStatistics.goalMoreThan2(probabilityMatrix);
        BigDecimal goalsMoreThan2odd=calculate(goalsMoreThan2Probability);
        return goalsMoreThan2odd;
    }

    private BigDecimal calcualteGoalsLessEquals2odds(double[][] probabilityMatrix) {
        double goalsLessEquals3Probability=matchStatistics.goalsLessEquals2(probabilityMatrix);
        BigDecimal goalsLessEquals3Odds=calculate(goalsLessEquals3Probability);
        return goalsLessEquals3Odds;
    }

    private BigDecimal calculateAwayTeamWinOdds(double[][] probabilityMatrix) {
        double awayTeamWinProbability=matchStatistics.awayTeamWin(probabilityMatrix);
        return calculate(awayTeamWinProbability);
    }

    private BigDecimal calculateDrawOdd(double[][] probabilityMatrix) {
        double drawProbability=matchStatistics.draw(probabilityMatrix);
        BigDecimal drawOdd=calculate(drawProbability);
        return drawOdd;
    }

    private BigDecimal calculateHomeTeamWinOdd(double[][] probabilityMatrix) {
        double homeTeamWinProbability=matchStatistics.homeTeamWin(probabilityMatrix);
        BigDecimal homeTeamWinnOdd=calculate(homeTeamWinProbability);
        return homeTeamWinnOdd;
    }

    private double[][] getProbabilityMatrix(Team home, Team away, Season season) {
        double homeTeamGoals = matchStatistics.homeTeamGoalsPrediction(home, away, season);
        double[] homeTeamGoalsProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(homeTeamGoals);
        double awayTeamGoals = matchStatistics.awayTeamGoalsPrediction(home, away, season);
        double[] awayTeamGoalsProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(awayTeamGoals);
        double[][] matchResultProbabilityMatix = matchStatistics.matchScoreProbabilityMatrix(homeTeamGoalsProbability, awayTeamGoalsProbability);
        return matchResultProbabilityMatix;
    }

}