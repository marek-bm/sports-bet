package pl.bets365mj.oddStatistics;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource (locations = "/application-test.properties")
public class MatchStatisticsTest {
    private static final Logger log = LoggerFactory.getLogger(MatchStatisticsTest.class);
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
    }

    @Test
    public void homeTeamsGoalsGlobalSeasonAvg() {
    }

    @Test
    public void awayTeamAttackStrength() {
    }

    @Test
    public void awayTeamDefensiveStrength() {
    }

    @Test
    public void awayTeamGoalsGlobalSeasonAvg() {
    }

    @Test
    public void awayTeamGoalsPrediction() {
    }

    @Test
    public void probabilityDistributionToScoreZeroToSixGoals() {
    }

    @Test
    public void matchScoreProbabilityMatrix() {
    }

    @Test
    public void homeTeamWin() {
    }

    @Test
    public void draw() {
    }

    @Test
    public void awayTeamWin() {
    }

    @Test
    public void goalsLessEquals2() {
    }

    @Test
    public void goalMoreThan2() {
    }
}