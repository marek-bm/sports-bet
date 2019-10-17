package pl.coderslab.sportsbet2.fixture.eventData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportsbet2.fixture.eventData.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {



}
