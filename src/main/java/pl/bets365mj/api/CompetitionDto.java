package pl.bets365mj.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.bets365mj.fixtureMisc.TableDto;

@NoArgsConstructor @AllArgsConstructor
@Data
public class CompetitionDto {
    @JsonProperty ("id") Long apiId;
    @JsonProperty ("name")String name;
    @JsonProperty ("currentSeason") TableDto.SeasonDto currentSeason;
}
