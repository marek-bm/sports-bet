package pl.bets365mj.fixtureMisc;

import java.util.List;

public interface SeasonResultsService {

    List<SeasonResult> findAllBySeasonOrderByPointsDesc(Season season);
}
