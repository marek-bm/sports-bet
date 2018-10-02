package pl.coderslab.sportsbet2.batchCsvConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.*;
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

        Team awayTeam=teamService.findTeamById(fixtureDTO.getAwayTeam_id());
        Team homeTeam=teamService.findTeamById(fixtureDTO.getHomeTeam_id());

        SportCategory category=sportCategoryService.findSportCategoryById(fixtureDTO.getCategory_id());
        League league=leagueService.findLeagueById(fixtureDTO.getLeague_id());
        Season season=seasonService.findById(fixtureDTO.getSeason_id());

        resultSolver(homeTeam, awayTeam, fixtureDTO, season);

        fixture.setDate(fixtureDTO.getDate());
        fixture.setFTAG(fixtureDTO.getFTAG());
        fixture.setFTHG(fixtureDTO.getFTHG());
        fixture.setFTR(fixtureDTO.getFTR());
        fixture.setHTAG(fixtureDTO.getHTAG());
        fixture.setHTHG(fixtureDTO.getHTHG());
        fixture.setHTR(fixtureDTO.getHTR());
        fixture.setMatchStatus(fixtureDTO.getMatchStatus());
        fixture.setMatchday(fixtureDTO.getMatchday());
        fixture.setAwayTeam(awayTeam);
        fixture.setCategory(category);
        fixture.setHomeTeam(homeTeam);
        fixture.setLeague(league);
        fixture.setSeason(season);

        log.info("Converting event on: "+fixtureDTO.getDate()+" matchday: "+ fixtureDTO.getMatchday());

        return fixture;
    }


    /**
     * Method which takes as input home team, away team, results obtained form fixtureDTO
     * and assing the scored goals and point to the particular team
     *
     * @param homeTeam
     * @param awayTeam
     * @param fixtureDTO
     * @param season
     */

    public static void resultSolver(Team homeTeam, Team awayTeam, FixtureDTO fixtureDTO, Season season){

        Result homeTeamResult=new Result();
        Result awayTeamResult=new Result();


        if(fixtureDTO.getFTR().equals("H")){
            homeTeamResult.setPoints(homeTeamResult.getPoints()+3);
            homeTeamResult.setWins(homeTeamResult.getWins()+1);
            awayTeamResult.setLost(awayTeamResult.getLost()+1);
        }
        else if(fixtureDTO.getFTR().equals("D")){
            homeTeamResult.setPoints(homeTeamResult.getPoints()+1);
            homeTeamResult.setDraws(homeTeamResult.getDraws()+1);
            awayTeamResult.setPoints(awayTeamResult.getPoints()+1);
            awayTeamResult.setDraws(awayTeamResult.getDraws()+1);
        }
        else {
            awayTeamResult.setPoints(awayTeamResult.getPoints()+3);
            homeTeamResult.setLost(homeTeamResult.getLost()+1);
            awayTeamResult.setWins(awayTeamResult.getWins()+1);
        }


        homeTeamResult.setSeason(season);
        awayTeamResult.setSeason(season);

        homeTeamResult.setTeam(homeTeam);
        awayTeamResult.setTeam(awayTeam);

        homeTeam.addResult(homeTeamResult);
        awayTeam.addResult(awayTeamResult);

        int goalsHomeTeam=fixtureDTO.getFTHG();
        int goalsAwayTeam=fixtureDTO.getFTAG();

        homeTeamResult.setGetGoalsScoredHome(homeTeamResult.getGetGoalsScoredHome()+goalsHomeTeam);
        homeTeamResult.setGetGoalsLostHome(homeTeamResult.getGetGoalsScoredHome()+goalsAwayTeam);

        awayTeamResult.setGoalsScoredAway(awayTeamResult.getGoalsScoredAway()+goalsAwayTeam);
        awayTeamResult.setGetGoalsLostAway(awayTeamResult.getGetGoalsLostAway()+goalsHomeTeam);

    }

}
