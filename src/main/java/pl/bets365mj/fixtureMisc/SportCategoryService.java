package pl.bets365mj.fixtureMisc;

import java.util.List;


public interface SportCategoryService {

    SportCategory findSportCategoryById(int id);
    List<SportCategory> findAll();
}
