package pl.bets365mj.fixture;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.Team;

import java.util.List;

@Repository
public interface FixtureRepository extends JpaRepository<Fixture, Integer> {

    @Override
    List<Fixture> findAll();
    List<Fixture> findAllByMatchdayAndSeason(int matchday, Season season);
    List<Fixture> findAllBySeasonAndMatchStatus(Season season, String status);
    List<Fixture> findAllBySeason(Season season);
    List<Fixture> findFixturesByHomeTeamAndSeasonAndMatchStatus(Team team, Season season, String status);
    List<Fixture> findFixturesByAwayTeamAndSeasonAndMatchStatus(Team team, Season season, String status);
    List<Fixture> findFixturesByHomeTeamAndAwayTeamAndMatchStatus(Team home, Team away, String status);

    @Query("SELECT f FROM Fixture f WHERE f.homeTeam = :team AND (f.season = :current OR f.season = :prev) AND f.matchStatus= 'finished' ORDER BY f.season DESC, f.matchday DESC")
    Page<Fixture> findTop5ByHomeTeam(@Param("team") Team team,
                                     @Param("current") Season currentSeason,
                                     @Param("prev") Season previousSeason,
                                     Pageable pageable);

    @Query("SELECT f FROM Fixture f WHERE f.awayTeam = :team AND (f.season = :current OR f.season = :prev) AND f.matchStatus= 'finished' ORDER BY f.season DESC, f.matchday DESC")
    Page<Fixture> findTop5ByAwayTeam(@Param("team") Team team,
                                     @Param("current") Season currentSeason,
                                     @Param("prev") Season previousSeason,
                                     Pageable pageable);

    List<Fixture> findAllByMatchStatus(String status);
    Fixture findById(int id);
    Fixture findByApiId(long id);
}
