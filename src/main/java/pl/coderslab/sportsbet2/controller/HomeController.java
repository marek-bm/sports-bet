package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.service.FixtureService;
import pl.coderslab.sportsbet2.service.SeasonService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    FixtureService fixtureService;

    @Autowired
    SeasonService seasonService;

    @RequestMapping("/")
    @ResponseBody
    public String home(){
        return "home page";
    }

    @RequestMapping("/admin")
    @ResponseBody
    public String admin(){
        return "admin page";
    }


    @GetMapping("/home")
    public String home(Model model) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        List<Fixture> activeFixtures=fixtureService.findAllByMatchStatus("active");

        model.addAttribute("events", activeFixtures);

        return "home";
    }








}
