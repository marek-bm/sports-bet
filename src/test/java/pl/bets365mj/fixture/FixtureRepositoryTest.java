package pl.bets365mj.fixture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FixtureRepositoryTest {

    @Test
    public void findTop5ByHomeTeam() {
    }

    @Test
    public void findTop5ByAwayTeam() {
    }
}
