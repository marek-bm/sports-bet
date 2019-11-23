package pl.bets365mj.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.HashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchDto {
    @JsonProperty("id")
    long apiMatchId;
    @JsonProperty("season")
    HashMap<String, String> season;
    @JsonProperty("utcDate")
    Date utcDate;
    @JsonProperty("status")
    String status;
    @JsonProperty("matchday")
    int matchday;
    @JsonProperty("score")
    ScoreDto score;
    @JsonProperty("homeTeam")
    TeamDto homeTeam;
    @JsonProperty("awayTeam")
    TeamDto awayTeam;
}
