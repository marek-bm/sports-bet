package pl.bets365mj.fixtureMisc;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;

@Data
public class TableDto {
    @JsonProperty ("competition") Competition competition;

    public TableDto(Competition competition) {
        this.competition = competition;
    }

    public TableDto() {
    }

    @Data
    private class Competition {
        @JsonProperty ("id") String id;
        @JsonProperty ("area") HashMap<String, String> area;
        @JsonProperty ("name") String name;
        @JsonProperty ("code") String code;

        public Competition(String id, HashMap<String, String> area, String name, String code) {
            this.id = id;
            this.area = area;
            this.name = name;
            this.code = code;
        }

        public Competition() {
        }
    }
}
