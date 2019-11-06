package pl.coderslab.sportsbet2.fixtureMisc;

import java.util.List;

public interface SeasonService {

    Season findById(int id);
    List<Season> findAll();
}

