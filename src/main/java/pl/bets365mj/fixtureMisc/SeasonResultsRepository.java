package pl.bets365mj.fixtureMisc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeasonResultsRepository extends JpaRepository<SeasonResult, Integer> {

//    @Query("select r from Result r where r.season=?1 order by r.points desc, (r.getGoalsScoredHome+r.goalsScoredAway) desc")
//    List<SeasonResult> findAllBySeasonAndSort(Season season, Sort points);
    List<SeasonResult> findAllBySeasonOrderByPointsDesc(Season season);
}
