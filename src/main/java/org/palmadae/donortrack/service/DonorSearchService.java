package org.palmadae.donortrack.service;

import org.palmadae.donortrack.dto.donorsearch.BloodStationDto;
import org.palmadae.donortrack.dto.donorsearch.DonorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DonorSearchService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<BloodStationDto> getStations(String citySlug) {

        String url = "https://api2.donorsearch.org/api/blood_stations/?city_slug=" + citySlug;

        ResponseEntity<DonorResponse> response =
                restTemplate.getForEntity(url, DonorResponse.class);

        return response.getBody().getResults();
    }
}