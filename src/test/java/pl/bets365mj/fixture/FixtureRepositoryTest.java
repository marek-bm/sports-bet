package pl.bets365mj.fixture;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource (locations = "/application-test.properties")
public class FixtureRepositoryTest {

    @Autowired
    FixtureRepository fixtureRepository;

    @Value("${foo}")
    String foo;

    @Test
    public void fooShoulBeEqualBar(){
        assertEquals(foo, "bar");
        System.out.println(foo);
    }

    @Test
    public void findTop5ByHomeTeam() {
    }

    @Test
    public void findTop5ByAwayTeam() {
    }
}
