package pl.coderslab.sportsbet2.fixtureMisc;

import java.util.List;

public interface TeamService {

    Team findTeamById(int id);
    Team findTeamByName(String name);
    List<Team> findAll();
}
