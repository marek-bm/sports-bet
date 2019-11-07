package pl.bets365mj.odd;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.Team;
import pl.bets365mj.fixture.Fixture;
import pl.bets365mj.fixture.FixtureService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MatchStatistics implements Statistics{
    /*
    calculation based on:
    https://help.smarkets.com/hc/en-gb/articles/115001457989-How-to-calculate-Poisson-distribution-for-football-betting
     */

    @Autowired
    FixtureService fixtureService;

    @Override
    public double homeTeamAttackStrength(Team team, Season season) {
        double homeTeamsGoalsGlobalAVG = this.homeTeamGoalsSeasonAvg(season);
        double homeTeamSeasonGoals = 0;
        double games = 0;

        List<Fixture> fixtures = fixtureService.findFixturesByHomeTeamAndSeasonAndMatchStatus(team, season, "finished");
        for (Fixture f : fixtures) {
            homeTeamSeasonGoals += f.getFTHG();
            games += 1;
        }

        double homeTeamAvgGoals = homeTeamSeasonGoals / games;
        double attackStrength = homeTeamAvgGoals / homeTeamsGoalsGlobalAVG;
        return attackStrength;
    }

    @Override
    public double homeTeamDefensiveStrength(Team team, Season season) {
        List<Fixture> fixtures = fixtureService.findFixturesByHomeTeamAndSeasonAndMatchStatus(team, season, "finished");
        double awayTeamGoalsSeasonAvg = this.awayTeamGoalsSeasonAvg(season);
        double totalGoals = 0;
        double games = 0;

        for (Fixture f : fixtures) {
            totalGoals += f.getFTAG();
            games += 1;
        }

        double homeTeamAVG = totalGoals / games;
        double deffenceStrength = homeTeamAVG / awayTeamGoalsSeasonAvg;
        return deffenceStrength;
    }

    @Override
    public double homeTeamGoalsPrediction(Team home, Team away, Season season) {
        double homeTeamAttackStrength = this.homeTeamAttackStrength(home, season);
        double awayTeamDeffenceStrength = this.awayTeamDefensiveStrength(away, season);
        double homeTeamGlobalsStrength = this.homeTeamGoalsSeasonAvg(season);
        double result = homeTeamAttackStrength * awayTeamDeffenceStrength * homeTeamGlobalsStrength;
        //to aviod arythmetiacl exception
        if (result == 0.0) {
            result = 0.001;
        }
        return result;
    }

    @Override
    public double homeTeamGoalsSeasonAvg(Season season) {
        double totalGoals = 0;
        double games = 0;
        List<Fixture> fixtures = fixtureService.findAllBySeasonAndMatchStatus(season, "finished");
        for (Fixture f : fixtures) {
            totalGoals += f.getFTHG();
            games += 1;
        }
        double homeAVG = totalGoals / games;
        return homeAVG;
    }

    @Override
    public double awayTeamGoalsPrediction(Team home, Team away, Season season) {
        double homeTeamDefenceStrength = this.homeTeamDefensiveStrength(home, season);
        double awayTeamAttackStrength = this.awayTeamAttackStrength(away, season);
        double awayTeamGlobalStrength = this.awayTeamGoalsSeasonAvg(season);
        double result = homeTeamDefenceStrength * awayTeamAttackStrength * awayTeamAttackStrength;

        //to avoid arthmetical exception in furtehr operation
        if (result == 0.0) {
            result = 0.001;
        }
        return result;
    }


    public double[] goalsNumberProbability(double goalsPrediction) {
        double[] goalsProbability = new double[6];
        for (int i = 0; i < goalsProbability.length; i++) {
            double probability = (((Math.pow(goalsPrediction, i)) * (Math.pow(Math.E, -goalsPrediction))) / CombinatoricsUtils.factorial(i));
            goalsProbability[i] = probability;
        }
        return goalsProbability;
    }

    public double[][] matchResultProbability(double[] homeTeamGoals, double[] awayTeamGoals) {
        double[][] matchResult = new double[6][6];
        for (int i = 0; i < homeTeamGoals.length; i++) {
            for (int j = 0; j < awayTeamGoals.length; j++) {
                double probability = homeTeamGoals[i] * awayTeamGoals[j];
                matchResult[i][j] = probability;
            }
        }
        return matchResult;
    }

    public Map<String, Double> odds(double[][] matchResultsProbabilityMatrix) {
        Map<String, Double> odds = new HashMap<>();
        double homeWin = 0;
        double draw = 0;
        double goalsLessThan2_5 = 0;

        for (int i = 0; i < matchResultsProbabilityMatrix.length; i++) {
            for (int j = 0; j < i; j++) {
                homeWin += matchResultsProbabilityMatrix[i][j];
            }
        }

        for (int i = 0; i < matchResultsProbabilityMatrix.length; i++) {
            draw += matchResultsProbabilityMatrix[i][i];
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                goalsLessThan2_5 += matchResultsProbabilityMatrix[i][j];
            }
        }

        double awayWin = 1 - (homeWin + draw);
        double goalsMoreThan2_5 = 1 - goalsLessThan2_5;
        odds.put("win", homeWin);
        odds.put("draw", draw);
        odds.put("lost", awayWin);
        odds.put("goalsLess", goalsLessThan2_5);
        odds.put("goalsMore", goalsMoreThan2_5);
        return odds;
    }

    public double awayTeamGoalsSeasonAvg(Season season) {
        double totalGoals = 0;
        double games = 0;
        List<Fixture> fixtures = fixtureService.findAllBySeasonAndMatchStatus(season, "finished");
        for (Fixture f : fixtures) {
            totalGoals += f.getFTAG();
            games += 1;
        }
        return totalGoals / games;
    }

    public double awayTeamDefensiveStrength(Team team, Season season) {
        List<Fixture> awayTeamFixtures = fixtureService.findFixturesByAwayTeamAndSeasonAndMatchStatus(team, season, "finished");
        double homeGlobalAVG = this.homeTeamGoalsSeasonAvg(season);
        ;
        double totalGoals = 0;
        double games = 0;

        for (Fixture f : awayTeamFixtures) {
            totalGoals += f.getFTHG();
            games += 1;
        }

        double awayTeamAVG = totalGoals / games;
        double deffensiveStrength = awayTeamAVG / homeGlobalAVG;
        return deffensiveStrength;
    }

    public double awayTeamAttackStrength(Team team, Season season) {
        List<Fixture> awayTeamFixtures = fixtureService.findFixturesByAwayTeamAndSeasonAndMatchStatus(team, season, "finished");
        double awayGlobalAVG = this.awayTeamGoalsSeasonAvg(season);
        double totalGoals = 0;
        double games = 0;

        for (Fixture f : awayTeamFixtures) {
            totalGoals += f.getFTAG();
            games += 1;
        }

        double awayTeamAVG = totalGoals / games;
        double attackStrength = awayTeamAVG / awayGlobalAVG;
        return attackStrength;
    }
}




