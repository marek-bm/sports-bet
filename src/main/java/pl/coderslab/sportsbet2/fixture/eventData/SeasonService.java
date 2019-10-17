package pl.coderslab.sportsbet2.fixture.eventData;

import pl.coderslab.sportsbet2.fixture.eventData.Season;

import java.util.List;

public interface SeasonService {

    Season findById(int id);
    List<Season> findAll();
}

