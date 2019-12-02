package pl.bets365mj.fixtureMisc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import pl.bets365mj.api.ApiDetails;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.SeasonRepository;
import pl.bets365mj.fixtureMisc.SeasonResult;
import pl.bets365mj.fixtureMisc.SeasonResultsService;

import java.util.*;

@Controller
public class TableController {

    @Autowired
    SeasonService seasonService;

    @Autowired
    SeasonResultsService seasonResultsService;

    @RequestMapping("/table")
    public String table(Model model){
        Season season=seasonService.findCurrent();
        List<SeasonResult> seasonResults=seasonResultsService.findAllBySeasonOrderByPointsDesc(season);
        model.addAttribute("seasonResults", seasonResults);
        return "table";
    }

    @GetMapping("/api-table")
    public String showApiTable(Model model){
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.set(ApiDetails.TOKEN, ApiDetails.TOKEN_KEY);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String URL= "http://api.football-data.org/v2/competitions/2021/standings";
        HttpEntity<String> httpEntity=new HttpEntity<>("parameters", httpHeaders);
        ResponseEntity<TableDto> responseEntity=restTemplate.exchange(URL, HttpMethod.GET, httpEntity, TableDto.class);
        HttpStatus responseStatus=responseEntity.getStatusCode();
        TableDto tableDto=responseEntity.getBody();
        List<ApiTable> table= tableDto.getStandings().get(0).getTable();
        model.addAttribute("apiTable", table);
        return "table";
    }
}
