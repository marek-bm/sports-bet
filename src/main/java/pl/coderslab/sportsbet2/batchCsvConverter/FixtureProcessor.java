package pl.coderslab.sportsbet2.batchCsvConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.service.*;

@Component
public class FixtureProcessor implements ItemProcessor<FixtureDTO, Fixture> {
    private static final Logger log = LoggerFactory.getLogger(FixtureProcessor.class);

    @Autowired
    CountryService countryService;

    @Autowired
    TeamService teamService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    LeagueService leagueService;

    @Autowired
    SportCategoryService sportCategoryService;


    @Override
    public Fixture process(FixtureDTO fixtureDTO) throws Exception {
        Fixture fixture=new Fixture();

        fixture.setDate(fixtureDTO.getDate());
        fixture.setFTAG(fixtureDTO.getFTAG());
        fixture.setFTHG(fixtureDTO.getFTHG());
        fixture.setFTR(fixtureDTO.getFTR());
        fixture.setHTAG(fixtureDTO.getHTAG());
        fixture.setHTHG(fixtureDTO.getHTHG());
        fixture.setHTR(fixtureDTO.getHTR());
        fixture.setMatchStatus(fixtureDTO.getMatchStatus());
        fixture.setMatchday(fixtureDTO.getMatchday());
        fixture.setAwayTeam(teamService.findTeamById(fixtureDTO.getAwayTeam_id()));
        fixture.setCategory(sportCategoryService.findSportCategoryById(fixtureDTO.getCategory_id()));
        fixture.setHomeTeam(teamService.findTeamById(fixtureDTO.getHomeTeam_id()));
        fixture.setLeague(leagueService.findLeagueById(fixtureDTO.getLeague_id()));
        fixture.setSeason(seasonService.findById(fixtureDTO.getSeason_id()));

        log.info("Converting even on: "+fixtureDTO.getDate()+" matchday: "+ fixtureDTO.getMatchday());

        return fixture;
    }
}
