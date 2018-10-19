package pl.coderslab.sportsbet2.service;

import pl.coderslab.sportsbet2.model.sportEvent.Season;

import java.util.List;

public interface SeasonService {
    Season findById(int id);
    List<Season> findAll();

}

