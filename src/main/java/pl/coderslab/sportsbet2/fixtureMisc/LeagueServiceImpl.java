package pl.coderslab.sportsbet2.fixtureMisc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeagueServiceImpl implements LeagueService {
    @Autowired
    LeagueRepository leagueRepository;

    @Override
    public League findLeagueById(int id) {
        return leagueRepository.getOne(id);
    }
}
