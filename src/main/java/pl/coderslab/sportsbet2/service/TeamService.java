package pl.coderslab.sportsbet2.service;

import pl.coderslab.sportsbet2.model.sportEvent.Team;

public interface TeamService {
    Team findTeamById(int id);

    Team findTeamByName(String name);
}
