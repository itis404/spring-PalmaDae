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

    private String o_plus;
    private String o_minus;

    private String a_plus;
    private String a_minus;

    private String b_plus;
    private String b_minus;

    private String ab_plus;
    private String ab_minus;
}