package pl.coderslab.sportsbet2.fixture.eventData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SportCategoryServiceImpl implements SportCategoryService {

    @Autowired
    SportCategoryRepository sportCategoryRepository;

    @Override
    public SportCategory findSportCategoryById(int id) {
        return sportCategoryRepository.getOne(id);
    }

    @Override
    public List<SportCategory> findAll() {
        return sportCategoryRepository.findAll();
    }
}
