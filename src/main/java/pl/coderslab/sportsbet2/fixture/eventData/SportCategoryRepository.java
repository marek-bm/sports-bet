package pl.coderslab.sportsbet2.fixture.eventData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportsbet2.fixture.eventData.SportCategory;

import java.util.List;

@Repository
public interface SportCategoryRepository extends JpaRepository<SportCategory, Integer> {

    SportCategory findSportCategoryById(int id);
    List<SportCategory> findAll();
}
