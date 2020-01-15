package pl.bets365mj.fixture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.SeasonRepository;
import pl.bets365mj.fixtureMisc.Team;
import pl.bets365mj.fixtureMisc.TeamRepository;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource (locations = "/application-test.properties")
public class FixtureRepositoryTest {

    @Autowired
    FixtureRepository fixtureRepository;

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    SeasonRepository seasonRepository;

    @Test
    public void findTop5ByHomeTeam() {
        int teamId=1;
        Team team=teamRepository.findTeamById(teamId);
        Season current=seasonRepository.findBySeasonYearIsStartingWith(2019);
        Season prev=seasonRepository.findBySeasonYearIsStartingWith(2018);
        List<Fixture> fixtures=fixtureRepository.findTop5ByHomeTeam(team,current,prev, PageRequest.of(0, 5)).getContent();
        fixtures.forEach(System.out::println);
        HashSet<Integer> idSet= (HashSet<Integer>) fixtures.stream().map(x->x.getHomeTeam().getId()).collect(Collectors.toSet());
        assert (idSet.size()==1 && idSet.contains(teamId));

    }


}
