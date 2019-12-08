package pl.bets365mj.fixture;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.bets365mj.api.MatchDto;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.Team;

import javax.transaction.Transactional;
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
    Map<Integer, List<Fixture>> groupByMatchday(List<Fixture> fixtures);
    Map<String, List<Fixture>> groupByStatus(List<Fixture> fixtures);
    Fixture convertDtoToFixtureEntity(MatchDto dto);
    Fixture updateFromDto(MatchDto dto);
    int getCurrentApiMatchday();
    public void wait60seconds();
    ResponseEntity<FixtureRoundDTO> apiGetRequestFixturesRound(String URL);
    MatchDto apiGetRequestMatchDto(long apiId);
    List<MatchDto> apiGetRequestForFixturesInMatchday(int matchday);

}
