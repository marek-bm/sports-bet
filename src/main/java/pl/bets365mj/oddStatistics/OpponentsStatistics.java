package pl.bets365mj.oddStatistics;

import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.Team;

import java.util.Map;

public interface OpponentsStatistics {

    double homeTeamAttackStrength(Team team, Season season);
    double homeTeamDefensiveStrength(Team team, Season season);
    double homeTeamsGoalsGlobalSeasonAvg(Season season);
    double homeTeamGoalsPrediction(Team home, Team away, Season season);

    double awayTeamDefensiveStrength(Team team, Season season);
    double awayTeamAttackStrength(Team team, Season season);
    double awayTeamGoalsGlobalSeasonAvg(Season season);
    double awayTeamGoalsPrediction(Team home, Team away, Season season);
    double[] probabilityDistributionToScoreZeroToSixGoals(double teamGoalsPrediction);
    double[][] matchScoreProbabilityMatrix(double[] homeTeamGoalsPrediction, double[] awayTeamGoalsPrediction);
}
