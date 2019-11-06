package pl.coderslab.sportsbet2.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.sportsbet2.fixtureMisc.Season;
import pl.coderslab.sportsbet2.fixtureMisc.SeasonResult;
import pl.coderslab.sportsbet2.fixtureMisc.SeasonRepository;
import pl.coderslab.sportsbet2.fixtureMisc.SeasonResultsService;

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
