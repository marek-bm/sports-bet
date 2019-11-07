package pl.bets365mj.fixtureMisc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportCategoryRepository extends JpaRepository<SportCategory, Integer> {

    SportCategory findSportCategoryById(int id);
    List<SportCategory> findAll();
}
