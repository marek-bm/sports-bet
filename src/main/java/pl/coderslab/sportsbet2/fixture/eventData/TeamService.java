package pl.coderslab.sportsbet2.fixture.eventData;

import pl.coderslab.sportsbet2.fixture.eventData.Team;

import java.util.List;

public interface TeamService {

    Team findTeamById(int id);
    Team findTeamByName(String name);
    List<Team> findAll();
}
