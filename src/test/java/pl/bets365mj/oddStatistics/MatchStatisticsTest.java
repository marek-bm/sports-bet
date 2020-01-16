package pl.bets365mj.oddStatistics;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.bets365mj.fixture.Fixture;
import pl.bets365mj.fixture.FixtureRepository;
import pl.bets365mj.fixture.FixtureService;
import pl.bets365mj.fixture.FixtureServiceImpl;
import pl.bets365mj.fixtureMisc.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource (locations = "/application-test.properties")
public class MatchStatisticsTest {
    FixtureService fixtureService;
    SeasonService seasonService;
    MatchStatistics matchStatistics;

    @Autowired
    FixtureRepository fixtureRepository;

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    TeamRepository teamRepository;

    @Before
    public void setUp(){
        seasonService=new SeasonServiceImpl(seasonRepository);
        fixtureService=new FixtureServiceImpl(fixtureRepository, seasonService);
        matchStatistics= new MatchStatistics(fixtureService, seasonService);
    }

    @Test
    public void homeTeamAttackStrength() {
        Season current=seasonRepository.findBySeasonYearIsStartingWith(2019);
        Season prev=seasonRepository.findBySeasonYearIsStartingWith(2018);
        Team homeTeam=teamRepository.findTeamById(16);
        List<Fixture> fixtures=fixtureRepository.findTop5ByHomeTeam(homeTeam,current,prev, PageRequest.of(0, 5)).getContent();
        int scoredGoals=fixtures.stream()
                .map( x-> x.getFTHG())
                .reduce(0, Integer::sum);
        double scoredAvg=(double)scoredGoals/fixtures.size();
        double homeTeamsGoalsScoredGlobalAVG=1.4663;
        final double expectedAttackStrength = 1.9095;
        double attackStrengthCalculatedInTest=scoredAvg/homeTeamsGoalsScoredGlobalAVG;
        double attackStrengthFromService=matchStatistics.homeTeamAttackStrength(homeTeam, current);
        assertEquals(expectedAttackStrength, attackStrengthCalculatedInTest,0.001);
        assertEquals(expectedAttackStrength, attackStrengthFromService, 0.001);
    }

    @Test
    public void homeTeamDefensiveStrength() {
        Season currentSeason=seasonRepository.findBySeasonYearIsStartingWith(2019);
        Team homeTeam=teamRepository.findTeamById(16);
        final double expectedStrength = 0.7704;
        double actualDefensiveStrength= matchStatistics.homeTeamDefensiveStrength(homeTeam, currentSeason);
        assertEquals(expectedStrength,actualDefensiveStrength , 0.001);
    }

    @Test
    public void homeTeamGoalsPrediction() {
        Team homeTeam=teamRepository.findTeamById(16);
        assert homeTeam.getName().equals("Man United");
        Team awayTeam=teamRepository.findTeamById(31);
        assert awayTeam.getName().equals("Wolves");
        Season currentSeason=seasonRepository.findBySeasonYearIsStartingWith(2019);
        double goalsPrediction=matchStatistics.homeTeamGoalsPrediction(homeTeam,awayTeam,currentSeason);
        final double expectedGoals=2.6733;
        assertEquals(expectedGoals,goalsPrediction, 0.001);
    }

    @Test
    public void homeTeamsGoalsGlobalSeasonAvg() {
    }

    @Test
    public void awayTeamAttackStrength() {
        Season currentSeason=seasonRepository.findBySeasonYearIsStartingWith(2019);
        Team team=teamRepository.findTeamById(16);
        final double expected=1.0785;
        double calculated=matchStatistics.awayTeamAttackStrength(team,currentSeason);
        assertEquals(expected, calculated, 0.001);
    }

    @Test
    public void awayTeamDefensiveStrength() {
        Season currentSeason=seasonRepository.findBySeasonYearIsStartingWith(2019);
        Team team=teamRepository.findTeamById(16);
        final double expected= 1.0911;
        double calculated=matchStatistics.awayTeamDefensiveStrength(team, currentSeason);
        assertEquals(expected, calculated, 0.001);
    }

    @Test
    public void awayTeamGoalsGlobalSeasonAvg() {
    }

    @Test
    public void awayTeamGoalsPrediction() {
        Team homeTeam=teamRepository.findTeamById(16);
        assert homeTeam.getName().equals("Man United");
        Team awayTeam=teamRepository.findTeamById(31);
        assert awayTeam.getName().equals("Wolves");
        Season currentSeason=seasonRepository.findBySeasonYearIsStartingWith(2019);
        final double expected=1.0785;
        double goalsPrediction=matchStatistics.awayTeamGoalsPrediction(homeTeam,awayTeam,currentSeason);
        assertEquals( expected, goalsPrediction, 0.001);
    }

    @Test
    public void probabilityDistributionToScoreZeroToSixGoals() {
        final double[] htExpectedProbability={0.0690,0.1845, 0.2466, 0.2198,0.1469, 0.0785};
        final double htGoalsPrediction=2.6733;
        final double[] atExpectedGoalsProbability={0.3401,0.3668,0.1978, 0.0711, 0.0192, 0.0041};
        final double atGoalsPrediction=1.0785;

        double[] htCalculatedProbability=matchStatistics.probabilityDistributionToScoreZeroToSixGoals(htGoalsPrediction);
        double[] atCalculatedProbability=matchStatistics.probabilityDistributionToScoreZeroToSixGoals(atGoalsPrediction);

        for(int i=0; i<htExpectedProbability.length; i++){
            assertEquals(htExpectedProbability[i], htCalculatedProbability[i], 0.001);
            assertEquals(atExpectedGoalsProbability[i], atCalculatedProbability[i], 0.001);
        }
    }

    @Test
    public void matchScoreProbabilityMatrix() {
        double[][] expectedMatrix={
                {0.0235, 0.0253, 0.0137, 0.0049, 0.0013, 0.0003},
                {0.0628, 0.0677, 0.0365, 0.0131, 0.0035, 0.0008},
                {0.0839, 0.0905, 0.0488, 0.0175, 0.0047, 0.0010},
                {0.0747, 0.0806, 0.0435, 0.0156, 0.0042, 0.0009},
                {0.0500, 0.0539, 0.0291, 0.0104, 0.0028, 0.0006},
                {0.0267, 0.0288, 0.0155, 0.0056, 0.0015, 0.0003}
        };

        final double htGoalsPrediction=2.6733;
        final double atGoalsPrediction=1.0785;
        double[] htScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(htGoalsPrediction);
        double[] atScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(atGoalsPrediction);
        double[][] calculatedMatrix = matchStatistics.matchScoreProbabilityMatrix(htScoreProbability, atScoreProbability);

        for(int i=0; i<expectedMatrix.length; i++){
           for (int j=0; j<calculatedMatrix.length; j++ ){
               double expected=expectedMatrix[i][j];
               double actual=calculatedMatrix[i][j];
               assertEquals(expected, actual, 0.01);
           }
        }
    }

    @Test
    public void homeTeamWin() {
        final double htGoalsPrediction=2.6733;
        final double atGoalsPrediction=1.0785;
        double[] htScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(htGoalsPrediction);
        double[] atScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(atGoalsPrediction);
        double[][] calculatedMatrix = matchStatistics.matchScoreProbabilityMatrix(htScoreProbability, atScoreProbability);
        double homeTeamWinProbability= matchStatistics.homeTeamWin(calculatedMatrix);
        double expectedHomeTeamProbability=0.6574;
        assertEquals(expectedHomeTeamProbability, homeTeamWinProbability, 0.001);
    }

    @Test
    public void draw() {
        final double htGoalsPrediction=2.6733;
        final double atGoalsPrediction=1.0785;
        double[] htScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(htGoalsPrediction);
        double[] atScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(atGoalsPrediction);
        double[][] calculatedMatrix = matchStatistics.matchScoreProbabilityMatrix(htScoreProbability, atScoreProbability);
        double drawProbability= matchStatistics.draw(calculatedMatrix);
        double expected=0.1587;
        assertEquals(expected, drawProbability,0.001);
    }

    @Test
    public void awayTeamWin() {
        final double htGoalsPrediction=2.6733;
        final double atGoalsPrediction=1.0785;
        double[] htScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(htGoalsPrediction);
        double[] atScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(atGoalsPrediction);
        double[][] calculatedMatrix = matchStatistics.matchScoreProbabilityMatrix(htScoreProbability, atScoreProbability);
        double awayTeamWinProbability= matchStatistics.awayTeamWin(calculatedMatrix);
        final double expectedProbability=0.1284;
        assertEquals(expectedProbability, awayTeamWinProbability, 0.001);
    }

    @Test
    public void goalsLessEquals2() {
        final double htGoalsPrediction=2.6733;
        final double atGoalsPrediction=1.0785;
        double[] htScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(htGoalsPrediction);
        double[] atScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(atGoalsPrediction);
        double[][] calculatedMatrix = matchStatistics.matchScoreProbabilityMatrix(htScoreProbability, atScoreProbability);
        double goalsLessEquals2Probability= matchStatistics.goalsLessEquals2(calculatedMatrix);
        final double expectedProbability=0.4525;
        assertEquals(expectedProbability, goalsLessEquals2Probability, 0.001);
    }

    @Test
    public void goalMoreThan2() {
        final double htGoalsPrediction=2.6733;
        final double atGoalsPrediction=1.0785;
        double[] htScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(htGoalsPrediction);
        double[] atScoreProbability = matchStatistics.probabilityDistributionToScoreZeroToSixGoals(atGoalsPrediction);
        double[][] calculatedMatrix = matchStatistics.matchScoreProbabilityMatrix(htScoreProbability, atScoreProbability);
        double goalsMoreThan2Probability= matchStatistics.goalMoreThan2(calculatedMatrix);
        final double expectedProbability=0.5475;
        assertEquals(expectedProbability, goalsMoreThan2Probability, 0.001);
    }
}