package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.SeasonResult;
import pl.coderslab.sportsbet2.repository.SeasonResultsRepository;
import pl.coderslab.sportsbet2.service.SeasonResultsService;

import java.util.List;

@Service
public class SeasonResultsServiceImpl implements SeasonResultsService {

    @Autowired
    SeasonResultsRepository seasonResultsRepository;

    @Override
    public List<SeasonResult> findAllBySeasonOrderByPointsDesc(Season season) {
        return seasonResultsRepository.findAllBySeasonOrderByPointsDesc(season);
    }
}
