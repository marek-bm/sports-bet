package pl.coderslab.sportsbet2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportsbet2.model.sportEvent.Season;

import java.util.List;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Integer> {

    @Query ("select max(s.id) from Season s")
    int findTopByIdAndOrderByIdDesc();

    List<Season> findAll();

}
