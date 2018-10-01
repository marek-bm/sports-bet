package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.SportCategory;
import pl.coderslab.sportsbet2.model.sportEvent.Team;
import pl.coderslab.sportsbet2.service.FixtureService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class FixtureController {
    @Autowired
    FixtureService fixtureService;

    @RequestMapping("/save")
    public String saveToDB() {


        Team home = new Team();
        home.setId(1);
        home.setName("Arsenal");

        Team away = new Team();
        away.setName("Aston Villa");
        away.setId(2);

        SportCategory category = new SportCategory();
        category.setId(1);
        category.setName("football");

        Fixture fix = new Fixture();

        DateFormat format = new SimpleDateFormat("dd/MM/yy");
        Date date = null;
        try {
            date = format.parse("18/08/12");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        fix.setDate(date);
        fix.setFTAG(0);
        fix.setFTHG(0);
        fix.setFTR("D");
        fix.setHTAG(0);
        fix.setHTHG(0);
        fix.setHTR('D');
        fix.setMatchStatus("finished");
        fix.setMatchday(1);
        fix.setAwayTeam(away);
        fix.setHomeTeam(home);
        fix.setCategory(category);


        fixtureService.saveFixture(fix);

        return "success";

    }
}
