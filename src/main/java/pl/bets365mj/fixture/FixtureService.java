package pl.bets365mj.fixture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bets365mj.api.MatchDto;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.Team;

import java.util.List;
import java.util.Map;

@Service
public interface FixtureService {

    Fixture save(Fixture fixture);
    List<Fixture> saveAll(List<Fixture> fixtures);
    List<Fixture> findAll();
    List<Fixture> findAllByMatchdayAndSeason(int matchday, Season season);
    List<Fixture> findAllBySeasonAndMatchStatus(Season season, String status);
    List<Fixture> findAllBySeason(Season season);
    List<Fixture> findFixturesByHomeTeamAndSeasonAndMatchStatus(Team team, Season season, String status);
    List<Fixture> findFixturesByAwayTeamAndSeasonAndMatchStatus(Team team, Season season, String status);
    List<Fixture> findFixturesByHomeTeamAndAwayTeamAndMatchStatus(Team home, Team away, String status);
    List<Fixture> findTop5ByHomeTeam(Team team, Season season);
    List<Fixture> findTop5ByAwayTeam(Team team, Season season);
    List<Fixture> findAllByMatchStatus(String status);
    Fixture findById(int id);
    Map<Integer, List<Fixture>> fixturesAsMapSortByMatchday(List<Fixture> currentSeasonGames);
    Fixture convertDtoToFixtureEntity(MatchDto dto);


}
