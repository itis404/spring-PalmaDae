package org.palmadae.donortrack.dto.donorsearch;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BloodStationDto {
    private Long id;
    private String title;
    private String address;
    @JsonProperty("blood_group")
    private List<String> bloodGroup;
    private Boolean closed;
}