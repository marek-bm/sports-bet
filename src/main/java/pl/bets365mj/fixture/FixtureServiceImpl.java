package pl.bets365mj.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bets365mj.api.MatchDto;
import pl.bets365mj.api.ScoreDto;
import pl.bets365mj.api.TeamDto;
import pl.bets365mj.fixtureMisc.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FixtureServiceImpl  implements FixtureService {

    @Autowired
    FixtureRepository fixtureRepository;

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    TeamService  teamService;

    @Autowired
    SeasonService seasonService;

    public Fixture save(Fixture fixture){
        return fixtureRepository.save(fixture);
    }

    @Override
    public List<Fixture> saveAll(List<Fixture> fixtures) {
        return fixtureRepository.saveAll(fixtures);
    }

    @Override
    public List<Fixture> findAll() {
        return fixtureRepository.findAll();
    }

    @Override
    public List<Fixture> findAllByMatchdayAndSeason(int matchday, Season season) {
        return fixtureRepository.findAllByMatchdayAndSeason(matchday,season);
    }

    @Override
    public List<Fixture> findAllBySeasonAndMatchStatus(Season season, String status) {
        return fixtureRepository.findAllBySeasonAndMatchStatus(season,status);
    }

    @Override
    public List<Fixture> findAllBySeason(Season season) {
        return fixtureRepository.findAllBySeason(season);
    }

    @Override
    public List<Fixture> findFixturesByHomeTeamAndSeasonAndMatchStatus(Team team, Season season, String status) {
        return fixtureRepository.findFixturesByHomeTeamAndSeasonAndMatchStatus(team, season,status);
    }

    @Override
    public List<Fixture> findFixturesByAwayTeamAndSeasonAndMatchStatus(Team team, Season season, String status) {
        return fixtureRepository.findFixturesByAwayTeamAndSeasonAndMatchStatus(team,season,status);
    }

    @Override
    public List<Fixture> findFixturesByHomeTeamAndAwayTeamAndMatchStatus(Team home, Team away, String status) {
        return fixtureRepository.findFixturesByHomeTeamAndAwayTeamAndMatchStatus(home, away,status);
    }

    @Override
    public List<Fixture> findTop5ByHomeTeam(Team team, Season currentSeason) {
        Season previousSeason=seasonService.findPrevious(currentSeason);
        return fixtureRepository.findTop5ByHomeTeam(team, currentSeason, previousSeason, PageRequest.of(0,5)).getContent();
    }

    @Override
    public List<Fixture> findTop5ByAwayTeam(Team team) {
        return fixtureRepository.findTop5ByAwayTeam(team, PageRequest.of(0, 5)).getContent();
    }

    @Override
    public List<Fixture> findAllByMatchStatus(String status) {
        return fixtureRepository.findAllByMatchStatus(status);
    }

    @Override
    public Fixture findById(int id) {
        return fixtureRepository.findById(id);
    }


    /*
     *following method takes as an input fixtures from selected season and creates map of
     * matchday vs. list of fixtures in a given matchday
     */
    public
    Map<Integer, List<Fixture>> fixturesAsMapSortByMatchday(List<Fixture> currentSeasonGames) {

        if (currentSeasonGames.size()>0) {
            Map<Integer, List<Fixture>> fixtureMap = new HashMap<>();

            Fixture f = currentSeasonGames.stream()
                    .collect(Collectors.minBy((x, y) -> x.getMatchday() - y.getMatchday()))
                    .get();

            int counter = f.getMatchday();

            fixtureMap.put(counter, new ArrayList<>());

            for (Fixture fixture : currentSeasonGames) {
                int matchday = fixture.getMatchday();

                if (counter == matchday) {
                    fixtureMap.get(counter).add(fixture);
                } else {
                    counter++;
                    fixtureMap.put(counter, new ArrayList<>());
                    fixtureMap.get(counter).add(fixture);
                }
            }
            return fixtureMap;
        }else {return null;}
    }

    @Override
    public Fixture convertDtoToFixtureEntity(MatchDto dto) {

        Fixture fixture=new Fixture();
        long matchIdDto=dto.getApiMatchId();
        fixture.setApiId(matchIdDto);

        //Home and Away team setup
        TeamDto homeTeamDto=dto.getHomeTeam();
        Team homeTeam=teamService.findByApiId(homeTeamDto.getApiTeamId());
        fixture.setHomeTeam(homeTeam);
        TeamDto awayTeamDto=dto.getAwayTeam();
        Team awayTeam=teamService.findByApiId(awayTeamDto.getApiTeamId());
        fixture.setAwayTeam(awayTeam);

        //Season setup
        HashMap<String, String> seasonDto=dto.getSeason();
        long seasonId= Long.parseLong(seasonDto.get("id"));
        Season season=seasonService.findByApiId(seasonId);
        fixture.setSeason(season);

        //Misc setup (Date, League, status etc
        Date matchDate=dto.getUtcDate();
        String status=dto.getStatus();
        int matchdayDto=dto.getMatchday();
        fixture.setMatchday(matchdayDto);
        Optional<League> league= leagueRepository.findById(1);
        fixture.setLeague(league.get());
        fixture.setDate(matchDate);
        fixture.setMatchStatus(status.toLowerCase());

        //Half Time Score
        ScoreDto score=dto.getScore();
        Map<String, Integer> halfTimeResult=score.getHalfTime();
        int halfTimefHomeTeamScore=halfTimeResult.get("homeTeam");
        int halfTimeAwayTeamScore=halfTimeResult.get("awayTeam");
        fixture.setHTHG(halfTimefHomeTeamScore);
        fixture.setHTAG(halfTimeAwayTeamScore);

        //Final Time Score
        Map<String, Integer> fullTimeResult=score.getFullTime();
        int fullTimefHomeTeamScore=fullTimeResult.get("homeTeam");
        int fullTimeAwayTeamScore=fullTimeResult.get("awayTeam");
        fixture.setFTHG(fullTimefHomeTeamScore);
        fixture.setFTAG(fullTimeAwayTeamScore);
        String winner=score.getWinner();
        fixture.setFTR(String.valueOf(winner.charAt(0)));

        return fixture;
    }
}
