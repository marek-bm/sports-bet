package pl.bets365mj.fixture;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor @AllArgsConstructor
public class FixtureDTO {
    @JsonProperty("count") int count;
    @JsonProperty("matches") List matches;

    @Data
    @NoArgsConstructor @AllArgsConstructor
    private class MatchApi{
        @JsonProperty("id") long apiMatchId;
        @JsonProperty("season") HashMap<String, String> season;
        @JsonProperty("utcDate") Date utcDate;
        @JsonProperty("status") String status;
        @JsonProperty("matchday") int matchday;
        @JsonProperty("score") Score score;
        @JsonProperty("homeTeam") TeamDto homeTeam;
        @JsonProperty("awayTeam") TeamDto awayTeam;
    }

    @Data
    @NoArgsConstructor @AllArgsConstructor
    private class Score{
        @JsonProperty("winner") String winner;
        @JsonProperty("fullTime") Map<String, Integer> fullTime;
        @JsonProperty("halfTime") Map<String, Integer> halfTime;
    }

    @Data
    @NoArgsConstructor @AllArgsConstructor
    private class TeamDto{
        @JsonProperty("id") long apiTeamId;
        @JsonProperty("name") String name;
    }
}
