package pl.bets365mj.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.SeasonRepository;
import pl.bets365mj.fixtureMisc.SeasonResult;
import pl.bets365mj.fixtureMisc.SeasonResultsService;

import java.util.List;

@Controller
public class TableController {

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    SeasonResultsService seasonResultsService;

    @RequestMapping("/table")
    public String table(Model model){
        Integer seasonId=seasonRepository.findTopByIdAndOrderByIdDesc();
        Season season=seasonRepository.getOne(seasonId);
        List<SeasonResult> seasonResults=seasonResultsService.findAllBySeasonOrderByPointsDesc(season);
        model.addAttribute("seasonResults", seasonResults);
        return "table";
    }
}
