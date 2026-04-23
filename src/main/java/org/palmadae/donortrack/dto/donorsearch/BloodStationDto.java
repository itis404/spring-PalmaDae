package org.palmadae.donortrack.dto.donorsearch;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BloodStationDto {

    public String title;
    public String address;

    @JsonProperty("blood_group")
    public List<String> bloodGroup;

    public Boolean closed;
}