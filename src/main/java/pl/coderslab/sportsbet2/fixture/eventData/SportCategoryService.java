package pl.coderslab.sportsbet2.fixture.eventData;

import pl.coderslab.sportsbet2.fixture.eventData.SportCategory;

import java.util.List;


public interface SportCategoryService {

    SportCategory findSportCategoryById(int id);
    List<SportCategory> findAll();
}
