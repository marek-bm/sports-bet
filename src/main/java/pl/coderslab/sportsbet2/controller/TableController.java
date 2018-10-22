package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.SeasonResult;
import pl.coderslab.sportsbet2.repository.SeasonRepository;
import pl.coderslab.sportsbet2.service.SeasonResultsService;

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
