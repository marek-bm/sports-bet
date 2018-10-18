package pl.coderslab.sportsbet2.API_Odds;

import org.apache.commons.math3.util.CombinatoricsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.coderslab.sportsbet2.model.Fixture;
import pl.coderslab.sportsbet2.model.sportEvent.Season;
import pl.coderslab.sportsbet2.model.sportEvent.Team;
import pl.coderslab.sportsbet2.service.FixtureService;

import java.util.List;

@Component
public class Statistics {

    @Autowired
    FixtureService fixtureService;

    public double homeTeamGoalsPrediction(Team home, Team away, Season season){
        double homeTeamAttackStrength=this.homeTeamAttackStrength(home, season);
        double awayTeamDeffenceStrength=this.awayTeamDeffensiveStrength(away,season);
        double homeTeamGlobalsStrength=this.homeTeamGoalsGlobalAVG(season);

        double result=homeTeamAttackStrength*awayTeamDeffenceStrength*homeTeamGlobalsStrength;

        return result;
    }

    public double awayTeamGoalsPrediction(Team home, Team away, Season season){
        double homeTeamDeffenceStrength=this.homeTeamDeffensiveStrength(home, season);
        double awayTeamAttackStrength=this.awayTeamAttackStrenght(away, season);
        double awayTeamGlobalStrength=this.awayTeamGoalsGlobalAVG(season);

        double result=homeTeamDeffenceStrength*awayTeamAttackStrength*awayTeamAttackStrength;

        return  result;
    }



    public double homeTeamGoalsGlobalAVG (Season season){
        double totalGoals=0;
        double games=0;
        List<Fixture> fixtures=fixtureService.findAllBySeasonAndMatchStatus(season, "finished");
        for (Fixture f: fixtures){
            totalGoals+=f.getFTHG();
            games+=1;

        }

        double homeAVG=totalGoals/games;
        return homeAVG;
    }


    public double[] goalsNumberProbability(double goalsPrediction){
        double[] goalsProbability=new double[6];

        for(int i=0; i<goalsProbability.length; i++){
            double probability=(((Math.pow(goalsPrediction, i)) *(Math.pow(Math.E, -goalsPrediction)))/ CombinatoricsUtils.factorial(i));
            goalsProbability[i]=probability;
        }

        return goalsProbability;
    }

    public double[][] matchResultProbability(double[] homeTeamGoals, double[] awayTeamGoals){
        double[][] matchResult=new double[6][6];

        for(int i=0; i<homeTeamGoals.length; i++){
            for(int j=0; j<awayTeamGoals.length; j++){
                double probability=homeTeamGoals[i]*awayTeamGoals[j]*100;
                matchResult[i][j]=probability;
            }
        }

        return matchResult;
    }


    public double awayTeamGoalsGlobalAVG(Season season){
        double totalGoals=0;
        double games=0;
        List<Fixture> fixtures=fixtureService.findAllBySeasonAndMatchStatus(season, "finished");
        for (Fixture f: fixtures){
            totalGoals+=f.getFTAG();
            games+=1;
        }
        return totalGoals/games;
    }




    // for global strength of away and home teams in league
    public double goalsSeasonAVG(double goalsTotal, double numberOfGames){
        return goalsTotal/numberOfGames;
    }



    public double homeTeamAttackStrength (Team team, Season season){

        List<Fixture> homeTeamFixtures=fixtureService.findFixturesByHomeTeamAndSeasonAndMatchStatus(team,season,"finished");

        double homeGlobalAVG=this.homeTeamGoalsGlobalAVG(season);

        double totalGoals=0;
        double games=0;

        for(Fixture f:homeTeamFixtures){
            totalGoals+=f.getFTHG();
            games+=1;
        }

        double homeTeamAVG=totalGoals/games;

        double attackStrength=homeTeamAVG/homeGlobalAVG;

        return attackStrength;
    }

    public double awayTeamDeffensiveStrength (Team team, Season season){

        List<Fixture> awayTeamFixtures=fixtureService.findFixturesByAwayTeamAndSeasonAndMatchStatus(team,season,"finished");

        double homeGlobalAVG=this.homeTeamGoalsGlobalAVG(season); ;

        double totalGoals=0;
        double games=0;

        for(Fixture f:awayTeamFixtures){
            totalGoals+=f.getFTHG();
            games+=1;
        }

        double awayTeamAVG=totalGoals/games;

        double deffensiveStrength=awayTeamAVG/homeGlobalAVG;

        return deffensiveStrength;
    }



    public double awayTeamAttackStrenght(Team team, Season season){
        List<Fixture> awayTeamFixtures=fixtureService.findFixturesByAwayTeamAndSeasonAndMatchStatus(team, season, "finished");

        double awayGlobalAVG=this.awayTeamGoalsGlobalAVG(season);

        double totalGoals=0;
        double games=0;

        for(Fixture f:awayTeamFixtures){
            totalGoals+=f.getFTAG();
            games+=1;
        }

        double awayTeamAVG=totalGoals/games;

        double attackStrength=awayTeamAVG/awayGlobalAVG;

        return attackStrength;
    }


    public double homeTeamDeffensiveStrength(Team team, Season season){
        List<Fixture> homeTeamFixtures=fixtureService.findFixturesByHomeTeamAndSeasonAndMatchStatus(team,season,"finished");

        double awayGlobalAVG=this.awayTeamGoalsGlobalAVG(season);

        double totalGoals=0;
        double games=0;

        for(Fixture f:homeTeamFixtures){
            totalGoals+=f.getFTAG();
            games+=1;
        }

        double homeTeamAVG=totalGoals/games;

        double deffenceStrength=homeTeamAVG/awayGlobalAVG;

        return deffenceStrength;
    }
}



