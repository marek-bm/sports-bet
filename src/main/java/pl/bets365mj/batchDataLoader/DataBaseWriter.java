package pl.bets365mj.batchDataLoader;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bets365mj.fixture.Fixture;
import pl.bets365mj.fixture.FixtureRepository;

import java.util.List;

@Component
public class DataBaseWriter implements ItemWriter<Fixture> {

    @Autowired
    FixtureRepository fixtureRepository;

    @Override
    public void write(List<? extends Fixture> fixtures) throws Exception {
//        fixtureRepository.save(fixtures);
        fixtureRepository.saveAll(fixtures);
    }
}
