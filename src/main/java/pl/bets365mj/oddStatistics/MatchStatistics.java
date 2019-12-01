package pl.bets365mj.oddStatistics;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.SeasonService;
import pl.bets365mj.fixtureMisc.Team;
import pl.bets365mj.fixture.Fixture;
import pl.bets365mj.fixture.FixtureService;

import java.util.List;

@Component
public class MatchStatistics implements MarketStatistics, OpponentsStatistics {
    /*
    calculation based on:
    https://help.smarkets.com/hc/en-gb/articles/115001457989-How-to-calculate-Poisson-distribution-for-football-betting
     */

    @Autowired
    FixtureService fixtureService;

    @Autowired
    SeasonService seasonService;

    @Override
    public double homeTeamAttackStrength(Team team, Season season) {
        double homeTeamsGoalsGlobalAVG = homeTeamsGoalsGlobalSeasonAvg(season);
        double scoredGoals = 0;
        double games = 0;

        List<Fixture> fixtures = fixtureService.findTop5ByHomeTeam(team, season);
        for (Fixture f : fixtures) {
            scoredGoals += f.getFTHG();
            games += 1;
        }
        double homeTeamAvgGoals = scoredGoals / games;
        double homeTeamAttackStrength = homeTeamAvgGoals / homeTeamsGoalsGlobalAVG;
        return homeTeamAttackStrength;
    }

    @Override
    public double homeTeamDefensiveStrength(Team team, Season season) {
        List<Fixture> fixtures = fixtureService.findTop5ByHomeTeam(team, season);
        double awayTeamGoalsSeasonAvg = awayTeamGoalsGlobalSeasonAvg(season);
        double totalGoals = 0;
        double games = 0;

        for (Fixture f : fixtures) {
            totalGoals += f.getFTAG();
            games += 1;
        }

        double homeTeamAVG = totalGoals / games;
        return homeTeamAVG/awayTeamGoalsSeasonAvg;
    }

    @Override
    public double homeTeamGoalsPrediction(Team homeTeam, Team awayTeam, Season season) {
        double homeTeamAttackStrength = homeTeamAttackStrength(homeTeam, season);
        double awayTeamDeffenceStrength = awayTeamDefensiveStrength(awayTeam, season);
        double homeTeamGlobalsStrength = homeTeamsGoalsGlobalSeasonAvg(season);
        double goalsPrediction = homeTeamAttackStrength * awayTeamDeffenceStrength * homeTeamGlobalsStrength;
        return goalsPrediction;
    }

    @Override
    public double homeTeamsGoalsGlobalSeasonAvg(Season season) {
        double totalGoals = 0;
        double games = 0;

        if(season.getCurrentMatchday()==1){
            Season previous=seasonService.findPrevious(season);
            List<Fixture> fixturesPrevSeason = fixtureService.findAllBySeason(previous);
            for (Fixture f : fixturesPrevSeason) {
                totalGoals += f.getFTHG();
                games += 1;
            }
        }
        else {
            List<Fixture> fixtures = fixtureService.findAllBySeasonAndMatchStatus(season, "finished");
            for (Fixture f : fixtures) {
                totalGoals += f.getFTHG();
                games += 1;
            }
        }
        double homeAVG = totalGoals / games;
        return homeAVG;
    }

    @Override
    public double awayTeamAttackStrength(Team team, Season season) {
        List<Fixture> awayTeamFixtures = fixtureService.findTop5ByAwayTeam(team, season);
        double awayGlobalAVG = this.awayTeamGoalsGlobalSeasonAvg(season);
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

    @Override
    public double awayTeamDefensiveStrength(Team team, Season season) {
        List<Fixture> awayTeamFixtures = fixtureService.findTop5ByAwayTeam(team, season);
        double homeGlobalAVG = homeTeamsGoalsGlobalSeasonAvg(season);
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

    @Override
    public double awayTeamGoalsGlobalSeasonAvg(Season season) {
        double totalGoals = 0;
        double games = 0;

        if(season.getCurrentMatchday()==1){
            Season previous=seasonService.findPrevious(season);
            List<Fixture> fixturesPrevSeason = fixtureService.findAllBySeason(previous);
            for (Fixture f : fixturesPrevSeason) {
                totalGoals += f.getFTAG();
                games += 1;
            }
        }
        else {
            List<Fixture> fixtures = fixtureService.findAllBySeasonAndMatchStatus(season, "finished");
            for (Fixture f : fixtures) {
                totalGoals += f.getFTHG();
                games += 1;
            }
        }
        return totalGoals / games;
    }

    @Override
    public double awayTeamGoalsPrediction(Team home, Team away, Season season) {
        double homeTeamDefenceStrength = this.homeTeamDefensiveStrength(home, season);
        double awayTeamAttackStrength = this.awayTeamAttackStrength(away, season);
        double awayTeamGlobalStrength = this.awayTeamGoalsGlobalSeasonAvg(season);
        double goalsPredicition = homeTeamDefenceStrength * awayTeamAttackStrength * awayTeamGlobalStrength;
        return goalsPredicition;
    }

    @Override
    public double[] probabilityDistributionToScoreZeroToSixGoals(double teamGoalsPrediction) {
        if(teamGoalsPrediction==0) {teamGoalsPrediction=0.1;}
        double[] goalsProbability = new double[6];
        for (int i = 0; i < goalsProbability.length; i++) {
            double probability = (((Math.pow(teamGoalsPrediction, i)) * (Math.pow(Math.E, -teamGoalsPrediction))) / CombinatoricsUtils.factorial(i));
            goalsProbability[i] = probability;
        }
        return goalsProbability;
    }

    @Override
    public double[][] matchScoreProbabilityMatrix(double[] homeTeamGoalsPredictedDistribution, double[] awayTeamGoalsPredictedDistribution) {
        double[][] matchResult = new double[6][6];
        for (int i = 0; i < homeTeamGoalsPredictedDistribution.length; i++) {
            for (int j = 0; j < awayTeamGoalsPredictedDistribution.length; j++) {
                double probability = homeTeamGoalsPredictedDistribution[i] * awayTeamGoalsPredictedDistribution[j];
                matchResult[i][j] = probability;
            }
        }
        return matchResult;
    }

    @Override
    public double homeTeamWin(double[][] matchResultsProbabilityMatrix) {
        double winningProbability = 0;
        for (int i = 0; i < matchResultsProbabilityMatrix.length; i++) {
            for (int j = 0; j < i; j++) {
                winningProbability += matchResultsProbabilityMatrix[i][j];
            }
        }
        return winningProbability;
    }

    @Override
    public double draw(double[][] matchResultsProbabilityMatrix) {
        double drawProbability=0;
        for (int i = 0; i < matchResultsProbabilityMatrix.length; i++) {
            drawProbability += matchResultsProbabilityMatrix[i][i];
        }
        return drawProbability;
    }

    @Override
    public double awayTeamWin(double[][] matchResultsProbabilityMatrix) {
        return 1-homeTeamWin(matchResultsProbabilityMatrix);
    }

    @Override
    public double goalsLessEquals2(double[][] matchResultsProbabilityMatrix) {
        double probabilityForLessThan3goals=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                probabilityForLessThan3goals += matchResultsProbabilityMatrix[i][j];
            }
        }
        return probabilityForLessThan3goals;
    }

    @Override
    public double goalMoreThan2(double[][] matchResultsProbabilityMatrix) {
        return 1-goalsLessEquals2(matchResultsProbabilityMatrix);
    }
}




