package pl.coderslab.sportsbet2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.sportsbet2.model.sportEvent.SportCategory;

@Repository
public interface SportCategoryRepository extends JpaRepository<SportCategory, Integer> {
    SportCategory findSportCategoryById(int id);
}
