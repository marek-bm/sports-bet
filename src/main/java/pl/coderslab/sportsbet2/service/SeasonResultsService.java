package pl.coderslab.sportsbet2.service;

import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.SeasonResult;

import java.util.List;

public interface SeasonResultsService {

//    List<SeasonResult> findAllBySeasonAndSort(Season season, Sort sort);

    List<SeasonResult> findAllBySeasonOrderByPointsDesc(Season season);
}
