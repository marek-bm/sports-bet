package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.sportEvent.Team;
import pl.coderslab.sportsbet2.repository.TeamRepository;
import pl.coderslab.sportsbet2.service.TeamService;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamRepository teamRepository;

    @Override
    public Team findTeamById(int id) {
        return teamRepository.getOne(id);
    }

    @Override
    public Team findTeamByName(String name) {
        return teamRepository.findTeamByName(name);
    }

    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }
}
