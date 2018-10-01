package pl.coderslab.sportsbet2.batchCsvConverter;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.repository.FixtureRepository;

import java.util.List;

@Component
public class BDWriter implements ItemWriter<Fixture> {

    @Autowired
    FixtureRepository fixtureRepository;

    @Override
    public void write(List<? extends Fixture> fixtures) throws Exception {
        fixtureRepository.save(fixtures);
    }
}
