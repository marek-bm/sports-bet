package pl.coderslab.sportsbet2.service;

import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.Team;

import java.util.List;
import java.util.Map;

@Service
public interface FixtureService {
    Fixture saveFixture(Fixture fixture);

    List<Fixture> saveFixtures(List<Fixture> fixtures);

    List<Fixture> findAll();

    List<Fixture> findAllByMatchdayAndSeason(int matchday, Season season);

    List<Fixture> findAllBySeasonAndMatchStatus(Season season, String status);

    List<Fixture> findAllBySeason(Season season);

    List<Fixture> findFixturesByHomeTeamAndSeasonAndMatchStatus(Team team, Season season, String status);
    List<Fixture> findFixturesByAwayTeamAndSeasonAndMatchStatus(Team team, Season season, String status);

    List<Fixture> findFixturesByHomeTeamAndAwayTeamAndMatchStatus(Team home, Team away, String status);

    List<Fixture> findTop5ByHomeTeam(Team team);
    List<Fixture> findTop5ByAwayTeam(Team team);

    List<Fixture> findAllByMatchStatus(String status);

    Fixture findById(int id);

    Map<Integer, List<Fixture>> fixturesAsMapSortByMatchday(List<Fixture> currentSeasonGames);


}
