package pl.coderslab.sportsbet2.service;

import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.Season;

import java.util.List;

@Service
public interface FixtureService {
    Fixture saveFixture(Fixture fixture);

    List<Fixture> saveFixtures(List<Fixture> fixtures);

    List<Fixture> findAll();

    List<Fixture> findAllByMatchdayAndSeason(int matchday, Season season);

    List<Fixture> findAllBySeasonAndMatchStatus(Season season, String status);

    List<Fixture> findAllBySeason(Season season);



}
