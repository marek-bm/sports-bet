package pl.bets365mj.fixtureMisc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {
    private SeasonRepository seasonRepository;

    public SeasonServiceImpl() {
    }

    public SeasonServiceImpl(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    @Override
    public Season findById(int id) {
        return seasonRepository.getOne(id);
    }

    @Override
    public List<Season> findAll() {
        return seasonRepository.findAll();
    }

    @Override
    public Season findCurrent() {
        int id=seasonRepository.findTop();
        Season season=findById(id);
        return season;
    }

    @Override
    public Season findByApiId(long apiId) {
        return seasonRepository.findByApiId(apiId);
    }

    @Override
    public Season findPrevious(Season currentSeason) {
        int currentYear= Integer.parseInt(currentSeason.getSeasonYear().substring(0,4));
        int prevTear=currentYear-1;
        return seasonRepository.findBySeasonYearIsStartingWith(prevTear);
    }

    @Override
    public Season save(Season season) {
        return seasonRepository.save(season);
    }

}
