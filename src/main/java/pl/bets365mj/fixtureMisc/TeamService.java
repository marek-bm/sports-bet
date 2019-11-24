package pl.bets365mj.fixtureMisc;

import java.util.List;

public interface TeamService {

    Team findTeamById(int id);
    Team findTeamByName(String name);
    List<Team> findAll();
    Team findByApiId(long id);
}
