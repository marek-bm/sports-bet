package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.sportsbet2.API_Odds.Odds;
import pl.coderslab.sportsbet2.BatchConverter.FixtureProcessor;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.League;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.SportCategory;
import pl.coderslab.sportsbet2.model.sportEvent.Team;
import pl.coderslab.sportsbet2.repository.LeagueRepository;
import pl.coderslab.sportsbet2.service.FixtureService;
import pl.coderslab.sportsbet2.service.SportCategoryService;
import pl.coderslab.sportsbet2.service.TeamService;
import pl.coderslab.sportsbet2.service.impl.SeasonServiceImpl;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping ("/results")
public class FixtureController {

    @Autowired
    SportCategoryService sportCategoryService;

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    FixtureService fixtureService;

    @Autowired
    SeasonServiceImpl seasonService;

    @Autowired
    TeamService teamService;

    @Autowired
    FixtureProcessor fixtureProcessor;

    @Autowired
    Odds odds;

    @RequestMapping ("/finished")
    public String resultsDisplay(Model model){
        Season currentSeason=seasonService.findById(7);

        List<Fixture> currentSeasonGames=fixtureService.findAllBySeason(currentSeason);

        Map<Integer, List<Fixture>> fixtureMap=fixturesAsMapSortByMatchday(currentSeasonGames);

        model.addAttribute("fixtures", fixtureMap);

        return "fragments/results-all";
    }


    @RequestMapping("/active")
    public String activeFixtures(Model model){
        Season currentSeason=seasonService.findById(7);

        List<Fixture> currentSeasonGames=fixtureService.findAllBySeasonAndMatchStatus(currentSeason, "active");

        Map<Integer, List<Fixture>> fixtureMap=fixturesAsMapSortByMatchday(currentSeasonGames);

        model.addAttribute("fixtures", fixtureMap);

        return "fragments/results-active";

    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editFixture(@PathVariable Integer id, Model model){
        Fixture fixture=fixtureService.findById(id);
        ArrayList<String> status=new ArrayList<>();
        status.add("finished");
        status.add("active");

        model.addAttribute("fixture", fixture);
        model.addAttribute("status", status);

        return "forms/fixture-edit";
    }

    @RequestMapping (value = "/edit", method = RequestMethod.POST)
    public String saveEditedFixture(@Valid Fixture fixture, BindingResult result){

        if (result.hasErrors()){
            return "forms/fixture-edit";
        }

        FixtureProcessor.resultSolver(fixture);
        fixtureService.saveFixture(fixture);

        return "redirect:/active";
    }


    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newFixtureStep1(Model model){

        Fixture fixture=new Fixture();
        model.addAttribute("fixture", fixture);

        return "forms/fixture-new";
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newFixtureStep2(@Valid Fixture fixture, BindingResult result, Model model ){

        if (result.hasErrors()){
            return "forms/fixture-new";
        }


        odds.fixtureOdds(fixture);
        fixtureService.saveFixture(fixture);
        return "redirect:/active";
    }



    @ModelAttribute
    public void listForFixtureForm(Model model){
        List<SportCategory> sports=sportCategoryService.findAll();
        model.addAttribute("sports", sports);

        List<League> leagues=leagueRepository.findAll();
        model.addAttribute("leagues", leagues);

        Season season=seasonService.findById(7);
        List<Season> seasons=new ArrayList<>();
        seasons.add(season);
        model.addAttribute("seasons", seasons);

        List<Team> teams=teamService.findAll();
        model.addAttribute("teams", teams);
    }











    /*
    *following method takes as an input fixtures from selected season and creates map of
    * matchday vs. list of fixtures in a given matchday
    */
    private Map<Integer, List<Fixture>> fixturesAsMapSortByMatchday(List<Fixture> currentSeasonGames) {
        Map<Integer, List<Fixture>> fixtureMap=new HashMap<>();

        Fixture f=currentSeasonGames.stream()
                .collect(Collectors.minBy((x,y)-> x.getMatchday()-y.getMatchday()))
                .get();

        int counter=f.getMatchday();

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
