package pl.bets365mj.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@NoArgsConstructor @AllArgsConstructor
public class ScoreDto {
    @JsonProperty("winner")
    String winner;
    @JsonProperty("fullTime")
    Map<String, Integer> fullTime;
    @JsonProperty("halfTime")
    Map<String, Integer> halfTime;
}
