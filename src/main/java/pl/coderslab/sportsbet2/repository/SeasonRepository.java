package pl.coderslab.sportsbet2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportsbet2.model.sportEvent.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {

}
