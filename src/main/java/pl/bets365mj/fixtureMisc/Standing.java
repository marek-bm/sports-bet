package pl.bets365mj.fixtureMisc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Standing {
    @JsonProperty("stage") String stage;
    @JsonProperty("type") String type;
    @JsonProperty("table") List<ApiTable> table;
}
