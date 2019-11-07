package pl.bets365mj.fixtureMisc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
