package pl.bets365mj.fixtureMisc;

import java.util.List;

public interface SeasonService {

    Season findById(int id);
    List<Season> findAll();
    Season findCurrent();
    Season findByApiId(long apiId);
    Season findPrevious(Season currentSeason);

}


