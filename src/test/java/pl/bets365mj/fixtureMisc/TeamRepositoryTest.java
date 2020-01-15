package pl.bets365mj.fixtureMisc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource (locations = "/application-test.properties")
public class TeamRepositoryTest {

    @Autowired
    TeamRepository teamRepository;

    @Test
    public void when_correct_id_then_findTeamById_return_not_null_team() {
        int id=1;
        Team team=teamRepository.findTeamById(id);
        assertNotNull(team);
        String expectedName="Arsenal";
        assertEquals(expectedName, team.getName());
    }

    @Test
    public void when_wrong_id_then_find_team_return_null() {
        int id=-1;
        Team team=teamRepository.findTeamById(id);
        assertNull(team);
    }

    @Test
    public void when_correct_name_then_findTeamByName_return_team() {
        String teamName="Everton";
        Team team=teamRepository.findTeamByName(teamName);
        assertNotNull(team);
        assertEquals(teamName, team.getName());
    }

    @Test
    public void findAll_should_return_not_empty_collection_with_specified_team_names() {
        List<Team> teams=teamRepository.findAll();
        String[] names={"Arsenal", "Brighton", "Chelsea", "Liverpool", "Man City", "Swansea"};
        List<String> expectedTeamNames= Arrays.asList(names);
        List<String> foundNames=teams.stream()
                .map(x->x.getName())
                .collect(Collectors.toList());
        assert teams.size()>30;
        assert foundNames.containsAll(expectedTeamNames);
    }
}