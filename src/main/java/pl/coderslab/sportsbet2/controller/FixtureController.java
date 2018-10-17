package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.service.FixtureService;
import pl.coderslab.sportsbet2.service.impl.SeasonServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FixtureController {

    @Autowired
    FixtureService fixtureService;

    @Autowired
    SeasonServiceImpl seasonService;

    @RequestMapping ("/results")
    public String resultsDisplay(Model model){
        Season currentSeason=seasonService.findById(7);

        List<Fixture> currentSeasonGames=fixtureService.findAllBySeason(currentSeason);

        Map<Integer, List<Fixture>> fixtureMap=fixturesAsMapSortByMatchday(currentSeasonGames);

        model.addAttribute("fixtures", fixtureMap);

        return "results";
    }






    /*
    *following method takes as an input fixtures from selected season and creates map of matchday vs. list of fixtures in a given matchday
    */
    private Map<Integer, List<Fixture>> fixturesAsMapSortByMatchday(List<Fixture> currentSeasonGames) {
        Map<Integer, List<Fixture>> fixtureMap=new HashMap<>();
        int counter=1;

        fixtureMap.put(counter,new ArrayList<>());

        for (Fixture fixture:currentSeasonGames){

            int matchday=fixture.getMatchday();
            if(counter==matchday){
                fixtureMap.get(counter).add(fixture);
            }
            else{
                counter++;
                fixtureMap.put(counter, new ArrayList<>());
                fixtureMap.get(counter).add(fixture);
            }
        }
        return fixtureMap;
    }


}
