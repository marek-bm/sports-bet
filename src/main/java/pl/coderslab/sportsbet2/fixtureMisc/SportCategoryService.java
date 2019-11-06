package pl.coderslab.sportsbet2.fixtureMisc;

import java.util.List;


public interface SportCategoryService {

    SportCategory findSportCategoryById(int id);
    List<SportCategory> findAll();
}
