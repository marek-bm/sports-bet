package pl.bets365mj.oddStatistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.SeasonService;
import pl.bets365mj.fixtureMisc.Team;
import pl.bets365mj.fixture.Fixture;
import pl.bets365mj.fixture.FixtureRepository;
import pl.bets365mj.odd.FootballOdd;

import java.math.BigDecimal;
import java.util.Map;

@Controller
public class MatchStatisticController {



    @Autowired
    FixtureRepository fixtureRepository;

    @Autowired
    SeasonService seasonService;

    @Autowired
    FootballOdd footballOdd;


}
