package pl.coderslab.sportsbet2.service;

import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.Fixture;

import java.util.List;

@Service
public interface FixtureService {
    Fixture saveFixture(Fixture fixture);

    List<Fixture> saveFixtures(List<Fixture> fixtures);



}
