package pl.coderslab.sportsbet2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.repository.SeasonRepository;
import pl.coderslab.sportsbet2.service.SeasonService;

import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    SeasonRepository seasonRepository;

    @Override
    public Season findById(int id) {
        return seasonRepository.getOne(id);
    }

    @Override
    public List<Season> findAll() {
        return seasonRepository.findAll();
    }


}
