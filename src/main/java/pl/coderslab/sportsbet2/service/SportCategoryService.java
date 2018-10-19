package pl.coderslab.sportsbet2.service;

import pl.coderslab.sportsbet2.model.sportEvent.SportCategory;

import java.util.List;


public interface SportCategoryService {
    SportCategory findSportCategoryById(int id);
    List<SportCategory> findAll();

}
