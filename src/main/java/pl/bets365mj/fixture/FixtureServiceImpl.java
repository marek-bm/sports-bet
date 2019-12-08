package pl.bets365mj.fixture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.bets365mj.api.ApiDetails;
import pl.bets365mj.api.MatchDto;
import pl.bets365mj.api.ScoreDto;
import pl.bets365mj.api.TeamDto;
import pl.bets365mj.bet.Bet;
import pl.bets365mj.bet.BetService;
import pl.bets365mj.coupon.Coupon;
import pl.bets365mj.coupon.CouponService;
import pl.bets365mj.fixtureMisc.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FixtureServiceImpl  implements FixtureService {

    @Autowired
    FixtureRepository fixtureRepository;

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    TeamService  teamService;

    @Autowired
    SeasonService seasonService;

    @Autowired
    CouponService couponService;

    @Autowired
    BetService betService;

    public Fixture save(Fixture fixture){
        return fixtureRepository.save(fixture);
    }

    @Override
    public List<Fixture> saveAll(List<Fixture> fixtures) {
        return fixtureRepository.saveAll(fixtures);
    }

    @Override
    public List<Fixture> findAll() {
        return fixtureRepository.findAll();
    }

    @Override
    public List<Fixture> findAllByMatchdayAndSeason(int matchday, Season season) {
        return fixtureRepository.findAllByMatchdayAndSeason(matchday,season);
    }

    @Override
    public List<Fixture> findAllBySeasonAndMatchStatus(Season season, String status) {
        return fixtureRepository.findAllBySeasonAndMatchStatus(season,status);
    }

    @Override
    public List<Fixture> findAllBySeason(Season season) {
        return fixtureRepository.findAllBySeason(season);
    }

    @Override
    public List<Fixture> findFixturesByHomeTeamAndSeasonAndMatchStatus(Team team, Season season, String status) {
        return fixtureRepository.findFixturesByHomeTeamAndSeasonAndMatchStatus(team, season,status);
    }

    @Override
    public List<Fixture> findFixturesByAwayTeamAndSeasonAndMatchStatus(Team team, Season season, String status) {
        return fixtureRepository.findFixturesByAwayTeamAndSeasonAndMatchStatus(team,season,status);
    }

    @Override
    public List<Fixture> findFixturesByHomeTeamAndAwayTeamAndMatchStatus(Team home, Team away, String status) {
        return fixtureRepository.findFixturesByHomeTeamAndAwayTeamAndMatchStatus(home, away,status);
    }

    @Override
    public List<Fixture> findTop5ByHomeTeam(Team team, Season currentSeason) {
        Season previousSeason=seasonService.findPrevious(currentSeason);
        return fixtureRepository.findTop5ByHomeTeam(team, currentSeason, previousSeason, PageRequest.of(0,5)).getContent();
    }

    @Override
    public List<Fixture> findTop5ByAwayTeam(Team team, Season currentSeason) {
        Season previousSeason=seasonService.findPrevious(currentSeason);
        return fixtureRepository.findTop5ByAwayTeam(team, currentSeason,previousSeason, PageRequest.of(0, 5)).getContent();
    }

    @Override
    public List<Fixture> findAllByMatchStatus(String status) {
        return fixtureRepository.findAllByMatchStatus(status);
    }

    @Override
    public Fixture findById(int id) {
        return fixtureRepository.findById(id);
    }

    public
    Map<Integer, List<Fixture>> groupByMatchday(List<Fixture> currentSeasonGames) {
        Map<Integer, List<Fixture>> mappedFixtures=currentSeasonGames.stream()
                .collect(Collectors.groupingBy(Fixture::getMatchday));
        return mappedFixtures;
    }

    @Override
    public Map<String, List<Fixture>> groupByStatus(List<Fixture> fixtures) {
        Map<String, List<Fixture>> mappedFixtures=fixtures.stream()
                .collect(Collectors.groupingBy(Fixture :: getMatchStatus));
        return mappedFixtures;
    }

    @Override
    public Fixture convertDtoToFixtureEntity(MatchDto dto) {

        Fixture fixture=new Fixture();
        long matchIdDto=dto.getApiMatchId();
        fixture.setApiId(matchIdDto);

        //Home and Away team setup
        TeamDto homeTeamDto=dto.getHomeTeam();
        Team homeTeam=teamService.findByApiId(homeTeamDto.getApiTeamId());
        fixture.setHomeTeam(homeTeam);
        TeamDto awayTeamDto=dto.getAwayTeam();
        Team awayTeam=teamService.findByApiId(awayTeamDto.getApiTeamId());
        fixture.setAwayTeam(awayTeam);

        //Season setup
        HashMap<String, String> seasonDto=dto.getSeason();
        long seasonId= Long.parseLong(seasonDto.get("id"));
        Season season=seasonService.findByApiId(seasonId);
        fixture.setSeason(season);

        //Misc setup (Date, League, status etc
        Date matchDate=dto.getUtcDate();
        String status=dto.getStatus();
        int matchdayDto=dto.getMatchday();
        fixture.setMatchday(matchdayDto);
        Optional<League> league= leagueRepository.findById(1);
        fixture.setLeague(league.get());
        fixture.setDate(matchDate);
        fixture.setMatchStatus(status.toLowerCase());

        //Half Time Score
        ScoreDto score=dto.getScore();
        if (status.equalsIgnoreCase("FINISHED")) {
            Map<String, Integer> halfTimeResult = score.getHalfTime();
            int halfTimefHomeTeamScore = halfTimeResult.get("homeTeam");
            int halfTimeAwayTeamScore = halfTimeResult.get("awayTeam");
            fixture.setHTHG(halfTimefHomeTeamScore);
            fixture.setHTAG(halfTimeAwayTeamScore);

            Map<String, Integer> fullTimeResult = score.getFullTime();
            int fullTimefHomeTeamScore = fullTimeResult.get("homeTeam");
            int fullTimeAwayTeamScore = fullTimeResult.get("awayTeam");
            fixture.setFTHG(fullTimefHomeTeamScore);
            fixture.setFTAG(fullTimeAwayTeamScore);
            fixture.setFTR(String.valueOf(dto.getStatus().charAt(0)));
        }
        return fixture;
    }

    @Override
    public Fixture updateFromDto(MatchDto dto) {
        long apiId=dto.getApiMatchId();
        Fixture fixture=fixtureRepository.findByApiId(apiId);
        String currentStatus=fixture.getMatchStatus();
        String updatedStatus=dto.getStatus();
        if(currentStatus.equalsIgnoreCase(updatedStatus)){
            return fixture;
        } else if(updatedStatus.equalsIgnoreCase("finished") && !currentStatus.equalsIgnoreCase("finished")) {
            resolve(dto, fixture);
        } else {
            fixture.update(dto);
            fixtureRepository.save(fixture);
        }
        return fixture;
    }

    private void resolve(MatchDto dto, Fixture fixture) {
        fixture.update(dto);
        fixtureRepository.save(fixture);
        List<Bet> bets=betService.updateBets(fixture);
        List<Coupon> coupons=couponService.findAllByBetsIn(bets);
        couponService.resolveCoupons(coupons);
    }

    @Override
    public int getCurrentApiMatchday() {
        String URL= ApiDetails.URL_MATCHES+"?matchday=1";
        ResponseEntity<FixtureRoundDTO> responseEntity= apiGetRequestFixturesRound(URL);
        int availableRequests= Integer.parseInt(responseEntity.getHeaders().get("X-Requests-Available-Minute").get(0));
        if(availableRequests==1){
            wait60seconds();
        }
        FixtureRoundDTO dto=responseEntity.getBody();
        int currentMatchdayApi= Integer.parseInt(dto.getMatches().get(0).getSeason().get("currentMatchday"));
        return currentMatchdayApi;
    }

    @Override
    public ResponseEntity<FixtureRoundDTO> apiGetRequestFixturesRound(String URL) {
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.set(ApiDetails.TOKEN, ApiDetails.TOKEN_KEY);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity=new HttpEntity<>("parameters", httpHeaders);
        return restTemplate.exchange(URL, HttpMethod.GET, httpEntity, FixtureRoundDTO.class);
    }

    @Override
    public MatchDto apiGetRequestMatchDto(long apiId) {
        String URL= ApiDetails.URL_MATCH + apiId;
        ResponseEntity<FixtureRoundDTO> responseEntity= apiGetRequestFixturesRound(URL);
        HttpStatus httpStatus=responseEntity.getStatusCode();
        System.out.println(httpStatus);
        int availableRequests= Integer.parseInt(responseEntity.getHeaders().get("X-Requests-Available-Minute").get(0));
        System.out.println("Available Requests "+ availableRequests);
        if(availableRequests==1){
            wait60seconds();
        }
        return responseEntity.getBody().getMatchDto();
    }

    @Override
    public List<MatchDto> apiGetRequestForFixturesInMatchday(int matchday) {
        String URL = ApiDetails.URL_MATCHES + "?matchday=" + matchday;
        ResponseEntity<FixtureRoundDTO> responseEntity = apiGetRequestFixturesRound(URL);
        int availableRequests= Integer.parseInt(responseEntity.getHeaders().get("X-Requests-Available-Minute").get(0));
        System.out.println("Available Requests "+ availableRequests);
        if(availableRequests==1){
            wait60seconds();
        }
        FixtureRoundDTO roundDTO = responseEntity.getBody();
        return roundDTO.getMatches();
    }

    public void wait60seconds() {
        try {
            System.out.println("Waiting 60 seconds for new requestes pool");
            Thread.sleep(61000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
