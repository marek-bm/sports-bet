package pl.coderslab.sportsbet2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.coderslab.sportsbet2.service.FixtureService;

@Controller
public class FixtureController {
    @Autowired
    FixtureService fixtureService;


}
