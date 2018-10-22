package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.sportEvent.League;
import pl.coderslab.sportsbet2.repository.LeagueRepository;
import pl.coderslab.sportsbet2.service.LeagueService;

@Service
public class LeagueServiceImpl implements LeagueService {
    @Autowired
    LeagueRepository leagueRepository;

    @Override
    public League findLeagueById(int id) {
        return leagueRepository.getOne(id);
    }
}
