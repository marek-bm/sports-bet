package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
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
}
