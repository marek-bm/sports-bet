package pl.coderslab.sportsbet2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.Team;

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

    List<Fixture> findTop5ByHomeTeam(Team team);
    List<Fixture> findTop5ByAwayTeam(Team team);

}
