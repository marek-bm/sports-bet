package pl.coderslab.sportsbet2.service;

import pl.coderslab.sportsbet2.model.sportEvent.Team;

import java.util.List;

public interface TeamService {
    Team findTeamById(int id);

    Team findTeamByName(String name);

    List<Team> findAll();
}
