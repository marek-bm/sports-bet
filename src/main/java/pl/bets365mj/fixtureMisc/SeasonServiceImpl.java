package pl.bets365mj.fixtureMisc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
