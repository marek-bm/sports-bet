package pl.bets365mj.fixtureMisc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bets365mj.api.TeamDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiTable{
    @JsonProperty("position") int position;
    @JsonProperty("team") TeamDto team;
    @JsonProperty("playedGames") String playedGames;
    @JsonProperty("won") int won;
    @JsonProperty("draw") int draw;
    @JsonProperty("lost") int lost;
    @JsonProperty("points") int points;
    @JsonProperty("goalsFor") int goalsFor;
    @JsonProperty("goalsAgainst") int goalsAgainst;
    @JsonProperty("goalDifference") int goalsDifference;
}
