package pl.bets365mj.fixtureMisc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "/application-test.properties")
public class SeasonRepositoryTest {

    @Autowired
    SeasonRepository seasonRepository;

    @Test
    public void findTop() {
        int id=seasonRepository.findTop();
        assertEquals(8, id);
    }
}