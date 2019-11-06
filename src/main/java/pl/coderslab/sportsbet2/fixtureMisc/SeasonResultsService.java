package pl.coderslab.sportsbet2.fixtureMisc;

import java.util.List;

public interface SeasonResultsService {

    List<SeasonResult> findAllBySeasonOrderByPointsDesc(Season season);
}
