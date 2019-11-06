package pl.coderslab.sportsbet2.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.fixtureMisc.Season;
import pl.coderslab.sportsbet2.fixtureMisc.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FixtureServiceImpl  implements FixtureService {

    @Autowired
    FixtureRepository fixtureRepository;

    public Fixture saveFixture(Fixture fixture){
        return fixtureRepository.save(fixture);
    }

    @Override
    public List<Fixture> saveFixtures(List<Fixture> fixtures) {
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
    public List<Fixture> findTop5ByHomeTeam(Team team) {
        return fixtureRepository.findTop5ByHomeTeam(team);
    }

    @Override
    public List<Fixture> findTop5ByAwayTeam(Team team) {
        return fixtureRepository.findTop5ByAwayTeam(team);
    }

    @Override
    public List<Fixture> findAllByMatchStatus(String status) {
        return fixtureRepository.findAllByMatchStatus(status);
    }

    @Override
    public Fixture findById(int id) {
        return fixtureRepository.getOne(id);
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
}
