package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.sportEvent.SportCategory;
import pl.coderslab.sportsbet2.repository.SportCategoryRepository;
import pl.coderslab.sportsbet2.service.SportCategoryService;

@Service
public class SportCategoryServiceImpl implements SportCategoryService {
    @Autowired
    SportCategoryRepository sportCategoryRepository;


    @Override
    public SportCategory findSportCategoryById(int id) {
        return sportCategoryRepository.findOne(id);
    }
}
