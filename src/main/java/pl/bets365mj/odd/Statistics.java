package pl.bets365mj.odd;

import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.Team;

import java.util.Map;

public interface Statistics {

    public double homeTeamAttackStrength(Team team, Season season);
    public double homeTeamDefensiveStrength(Team team, Season season);
    public double homeTeamGoalsSeasonAvg(Season season);
    public double homeTeamGoalsPrediction(Team home, Team away, Season season);

    public double awayTeamDefensiveStrength(Team team, Season season);
    public double awayTeamAttackStrength(Team team, Season season);
    public double awayTeamGoalsSeasonAvg(Season season);
    public double awayTeamGoalsPrediction(Team home, Team away, Season season);

    public double[][] matchResultProbability(double[] homeTeamGoals, double[] awayTeamGoals);
    public Map<String, Double> odds(double[][] matchResultsProbabilities);
}
