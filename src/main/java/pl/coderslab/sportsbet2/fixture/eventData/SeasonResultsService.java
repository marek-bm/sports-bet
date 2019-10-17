package pl.coderslab.sportsbet2.fixture.eventData;

import pl.coderslab.sportsbet2.fixture.eventData.Season;
import pl.coderslab.sportsbet2.fixture.eventData.SeasonResult;

import java.util.List;

public interface SeasonResultsService {

    List<SeasonResult> findAllBySeasonOrderByPointsDesc(Season season);
}
