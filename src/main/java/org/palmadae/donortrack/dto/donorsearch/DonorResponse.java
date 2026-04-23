package org.palmadae.donortrack.dto.donorsearch;

import lombok.Data;

import java.util.List;

@Data
public class DonorResponse {
    private List<BloodStationDto> results;

    public List<BloodStationDto> getResults() {
        return results;
    }

    public void setResults(List<BloodStationDto> results) {
        this.results = results;
    }
}