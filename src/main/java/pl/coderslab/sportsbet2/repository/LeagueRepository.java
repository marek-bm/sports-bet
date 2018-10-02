package pl.coderslab.sportsbet2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportsbet2.model.sportEvent.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {
    League findLeagueById(int id);
}