package org.mcvly.refactoring.first_example.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PerformanceDTO
{
    private final String playId;
    private final int audience;

    @JsonCreator
    public PerformanceDTO(@JsonProperty("playID") String playId, @JsonProperty("audience") int audience)
    {
        this.playId = playId;
        this.audience = audience;
    }
}
