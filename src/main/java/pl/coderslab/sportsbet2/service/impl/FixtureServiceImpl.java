package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.Team;
import pl.coderslab.sportsbet2.repository.FixtureRepository;
import pl.coderslab.sportsbet2.service.FixtureService;

import java.util.List;

@Service
public class FixtureServiceImpl  implements FixtureService {

    @Autowired
    FixtureRepository fixtureRepository;

    public Fixture saveFixture(Fixture fixture){
        return fixtureRepository.save(fixture);
    }

    @Override
    public List<Fixture> saveFixtures(List<Fixture> fixtures) {
        return fixtureRepository.save(fixtures);
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
        return fixtureRepository.findOne(id);
    }
}
