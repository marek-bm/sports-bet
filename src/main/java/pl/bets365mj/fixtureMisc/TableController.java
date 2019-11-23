package pl.bets365mj.fixtureMisc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import pl.bets365mj.fixtureMisc.Season;
import pl.bets365mj.fixtureMisc.SeasonRepository;
import pl.bets365mj.fixtureMisc.SeasonResult;
import pl.bets365mj.fixtureMisc.SeasonResultsService;

import java.util.List;

@Controller
public class TableController {

    @Autowired
    SeasonRepository seasonRepository;

    @Autowired
    SeasonResultsService seasonResultsService;

    @RequestMapping("/table")
    public String table(Model model){
        Integer seasonId=seasonRepository.findTopByIdAndOrderByIdDesc();
        Season season=seasonRepository.getOne(seasonId);
        List<SeasonResult> seasonResults=seasonResultsService.findAllBySeasonOrderByPointsDesc(season);
        model.addAttribute("seasonResults", seasonResults);
        return "table";
    }

    @RequestMapping ("/api-table")
    @ResponseBody
    public void showApiTable(){
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.set("X-Auth-Token", "aa328c4c0d484c18b588362d5de22ec9");
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String URL= "http://api.football-data.org/v2/competitions/2021/standings";
        HttpEntity<String> httpEntity=new HttpEntity<>("parameters", httpHeaders);

        ResponseEntity<TableDto> responseEntity=restTemplate.exchange(URL, HttpMethod.GET, httpEntity, TableDto.class);
        HttpStatus responseStatus=responseEntity.getStatusCode();
        System.out.println(responseStatus);
        System.out.println(responseEntity.getBody().toString());
    }
}
