package pl.bets365mj.fixtureMisc;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class TableDto {
    @JsonProperty ("competition") Competition competition;
    @JsonProperty ("season") SeasonDto season;
    @JsonProperty("standings") List<Standing> standings;

    @Data
    @NoArgsConstructor @AllArgsConstructor
    private class Competition {
        @JsonProperty ("id") String id;
        @JsonProperty ("area") HashMap<String, String> area;
        @JsonProperty ("name") String name;
        @JsonProperty ("code") String code;
        @JsonProperty("lastUpdated") Date update;
    }

    @Data
    @NoArgsConstructor @AllArgsConstructor
    private class SeasonDto {
        @JsonProperty ("currentMatchday") int currentMatchday;
    }

    @Data
    @NoArgsConstructor @AllArgsConstructor
    private class Standings {
        @JsonProperty("type") String type;
        @JsonProperty("table") List<ApiTable> table;
    }

    @Data
    @NoArgsConstructor @AllArgsConstructor
    private class TeamDto{
        @JsonProperty("name") String name;
        @JsonProperty("crestUrl") String crestUrl;
    }

}
