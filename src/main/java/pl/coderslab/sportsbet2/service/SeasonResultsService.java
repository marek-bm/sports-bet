package pl.coderslab.sportsbet2.service;

import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.SeasonResult;

import java.util.List;

public interface SeasonResultsService {

    List<SeasonResult> findAllBySeasonOrderByPointsDesc(Season season);
}
