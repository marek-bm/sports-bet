package pl.bets365mj.oddStatistics;

public interface MarketStatistics {
    double homeTeamWin(double[][] matchResultsProbabilityMatrix);
    double draw(double[][] matchResultsProbabilityMatrix);
    double awayTeamWin(double[][] matchResultsProbabilityMatrix);
    double goalsLessEquals2(double[][] matchResultsProbabilityMatrix);
    double goalMoreThan2(double[][] matchResultsProbabilityMatrix);
}
