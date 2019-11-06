package pl.bets365mj.fixtureMisc;

import java.util.List;

public interface SeasonService {

    Season findById(int id);
    List<Season> findAll();
}

