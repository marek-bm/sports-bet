package pl.bets365mj.fixture;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.bets365mj.api.MatchDto;

import java.util.*;

@Data
@NoArgsConstructor @AllArgsConstructor
public class FixtureDTO {
    @JsonProperty("count")
    int count;
    @JsonProperty("matches") List<MatchDto> matches;
}
