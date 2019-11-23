package pl.bets365mj.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class TeamDto {
    @JsonProperty("id")
    long apiTeamId;
    @JsonProperty("name")
    String name;
}
