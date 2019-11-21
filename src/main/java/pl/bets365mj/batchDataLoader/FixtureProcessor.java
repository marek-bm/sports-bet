package pl.bets365mj.batchDataLoader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bets365mj.fixture.Fixture;
import pl.bets365mj.fixture.FixtureDTO;
import pl.bets365mj.fixtureMisc.*;

import java.util.Map;

@Component
public class FixtureProcessor implements ItemProcessor<FixtureDTO, Fixture> {
    private static final Logger log = LoggerFactory.getLogger(FixtureProcessor.class);

    @Autowired
    CountryService countryService;

    @Autowired
    TeamService teamService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    LeagueService leagueService;

    @Autowired
    SportCategoryService sportCategoryService;

    @Override
    public Fixture process(FixtureDTO fixtureDTO) throws Exception {
        Fixture fixture = new Fixture();

        Team awayTeam = teamService.findTeamByName(fixtureDTO.getAwayTeam());
        Team homeTeam = teamService.findTeamByName(fixtureDTO.getHomeTeam());

        SportCategory category = sportCategoryService.findSportCategoryById(fixtureDTO.getCategory_id());
        League league = leagueService.findLeagueById(fixtureDTO.getLeague_id());
        Season season = seasonService.findById(fixtureDTO.getSeason_id());

        resultSolver(homeTeam, awayTeam, fixtureDTO, season);
        fixture.setDate(fixtureDTO.getDate());
        fixture.setFTAG(fixtureDTO.getFTAG());
        fixture.setFTHG(fixtureDTO.getFTHG());
        fixture.setFTR(fixtureDTO.getFTR());
        fixture.setHTAG(fixtureDTO.getHTAG());
        fixture.setHTHG(fixtureDTO.getHTHG());
        fixture.setHTR(fixtureDTO.getHTR());
        fixture.setMatchStatus(fixtureDTO.getMatchStatus());
        fixture.setMatchday(fixtureDTO.getMatchday());
        fixture.setAwayTeam(awayTeam);
        fixture.setCategory(category);
        fixture.setHomeTeam(homeTeam);
        fixture.setLeague(league);
        fixture.setSeason(season);
        fixture.setHomeWinOdd(fixtureDTO.getHomeWinOdd());
        fixture.setDrawOdd(fixtureDTO.getDrawOdd());
        fixture.setAwayWinOdd(fixtureDTO.getAwayWinOdd());
        fixture.setGoalsMoreThan2odd(fixtureDTO.getGoal_less_2_5());
        fixture.setGoalsLessOrEquals2odd(fixtureDTO.getGoal_more_2_5());
        log.info("Converting event on: " + fixtureDTO.getDate() + " matchday: " + fixtureDTO.getMatchday());
        return fixture;
    }

    /**
     * Method which calculates season result for each team.
     * It takes as input home team, away team, results obtained form fixtureDTO
     * and assing the scored goals and point to the particular team
     *
     * @param homeTeam
     * @param awayTeam
     * @param fixtureDTO
     * @param season
     */
    public static void resultSolver(Team homeTeam, Team awayTeam, FixtureDTO fixtureDTO, Season season) {
        try {
            Map<Season, SeasonResult> homeTeamResults = homeTeam.getResults();
            SeasonResult homeTeamResult = homeTeamResults.get(season);
            if (homeTeamResult == null) {
                homeTeamResult = new SeasonResult();
            }

            Map<Season, SeasonResult> awayTeamResults = awayTeam.getResults();
            SeasonResult awayTeamResult = awayTeamResults.get(season);
            if (awayTeamResult == null) {
                awayTeamResult = new SeasonResult();
            }
            if (fixtureDTO.getFTR().equals("H")) {
                homeTeamResult.setPoints(homeTeamResult.getPoints() + 3);
                homeTeamResult.setWins(homeTeamResult.getWins() + 1);
                awayTeamResult.setLost(awayTeamResult.getLost() + 1);
            } else if (fixtureDTO.getFTR().equals("D")) {
                homeTeamResult.setPoints(homeTeamResult.getPoints() + 1);
                homeTeamResult.setDraws(homeTeamResult.getDraws() + 1);
                awayTeamResult.setPoints(awayTeamResult.getPoints() + 1);
                awayTeamResult.setDraws(awayTeamResult.getDraws() + 1);
            } else {
                awayTeamResult.setPoints(awayTeamResult.getPoints() + 3);
                homeTeamResult.setLost(homeTeamResult.getLost() + 1);
                awayTeamResult.setWins(awayTeamResult.getWins() + 1);
            }

            homeTeamResult.setSeason(season);
            awayTeamResult.setSeason(season);
            homeTeamResult.setTeam(homeTeam);
            awayTeamResult.setTeam(awayTeam);
//        homeTeam.addResult(homeTeamResult);
//        awayTeam.addResult(awayTeamResult);
            int goalsHomeTeam = fixtureDTO.getFTHG();
            int goalsAwayTeam = fixtureDTO.getFTAG();
            homeTeamResult.setGoalsScoredHome(homeTeamResult.getGoalsScoredHome() + goalsHomeTeam);
            homeTeamResult.setGoalsLostHome(homeTeamResult.getGoalsScoredHome() + goalsAwayTeam);
            awayTeamResult.setGoalsScoredAway(awayTeamResult.getGoalsScoredAway() + goalsAwayTeam);
            awayTeamResult.setGoalsLostAway(awayTeamResult.getGoalsLostAway() + goalsHomeTeam);
            homeTeamResults.put(season, homeTeamResult);
            awayTeamResults.put(season, awayTeamResult);
            homeTeam.setResults(homeTeamResults);
            awayTeam.setResults(awayTeamResults);
            homeTeamResult.setPlayedGames(homeTeamResult.getPlayedGames() + 1);
            awayTeamResult.setPlayedGames(awayTeamResult.getPlayedGames() + 1);
        } catch (NullPointerException e) {

        }
    }

    public static void resultSolver(Fixture fixture) {
        Team homeTeam = fixture.getHomeTeam();
        Team awayTeam = fixture.getAwayTeam();
        Season season = fixture.getSeason();
        try {
            Map<Season, SeasonResult> homeTeamResults = homeTeam.getResults();
            SeasonResult homeTeamResult = homeTeamResults.get(season);
            if (homeTeamResult == null) {
                homeTeamResult = new SeasonResult();
            }
            Map<Season, SeasonResult> awayTeamResults = awayTeam.getResults();
            SeasonResult awayTeamResult = awayTeamResults.get(season);
            if (awayTeamResult == null) {
                awayTeamResult = new SeasonResult();
            }
            if (fixture.getFTR().equals("H")) {
                homeTeamResult.setPoints(homeTeamResult.getPoints() + 3);
                homeTeamResult.setWins(homeTeamResult.getWins() + 1);
                awayTeamResult.setLost(awayTeamResult.getLost() + 1);
            } else if (fixture.getFTR().equals("D")) {
                homeTeamResult.setPoints(homeTeamResult.getPoints() + 1);
                homeTeamResult.setDraws(homeTeamResult.getDraws() + 1);
                awayTeamResult.setPoints(awayTeamResult.getPoints() + 1);
                awayTeamResult.setDraws(awayTeamResult.getDraws() + 1);
            } else {
                awayTeamResult.setPoints(awayTeamResult.getPoints() + 3);
                homeTeamResult.setLost(homeTeamResult.getLost() + 1);
                awayTeamResult.setWins(awayTeamResult.getWins() + 1);
            }

            homeTeamResult.setSeason(season);
            awayTeamResult.setSeason(season);
            homeTeamResult.setTeam(homeTeam);
            awayTeamResult.setTeam(awayTeam);
//        homeTeam.addResult(homeTeamResult);
//        awayTeam.addResult(awayTeamResult);
            int goalsHomeTeam = fixture.getFTHG();
            int goalsAwayTeam = fixture.getFTAG();
            homeTeamResult.setGoalsScoredHome(homeTeamResult.getGoalsScoredHome() + goalsHomeTeam);
            homeTeamResult.setGoalsLostHome(homeTeamResult.getGoalsScoredHome() + goalsAwayTeam);
            awayTeamResult.setGoalsScoredAway(awayTeamResult.getGoalsScoredAway() + goalsAwayTeam);
            awayTeamResult.setGoalsLostAway(awayTeamResult.getGoalsLostAway() + goalsHomeTeam);
            homeTeamResults.put(season, homeTeamResult);
            awayTeamResults.put(season, awayTeamResult);
            homeTeam.setResults(homeTeamResults);
            awayTeam.setResults(awayTeamResults);
            homeTeamResult.setPlayedGames(homeTeamResult.getPlayedGames() + 1);
            awayTeamResult.setPlayedGames(awayTeamResult.getPlayedGames() + 1);
        } catch (NullPointerException e) {

        }
    }
}
