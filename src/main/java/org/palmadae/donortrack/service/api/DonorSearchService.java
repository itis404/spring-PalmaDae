package org.palmadae.donortrack.service.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.palmadae.donortrack.dto.donorsearch.BloodStationDto;
import org.palmadae.donortrack.exception.custom.api.donorsearch.DonorSearchUnavailableException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonorSearchService {

    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<BloodStationDto> getStations(String citySlug) {

        String url = "https://api2.donorsearch.org/api/blood_stations/?city_slug=" + citySlug;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("User-Agent", "DonorTrack/1.0")
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful() || response.body() == null) {
                return new ArrayList<>();
            }

            String responseBody = response.body().string();
            JsonNode root = objectMapper.readTree(responseBody);

            List<BloodStationDto> stations = new ArrayList<>();
            JsonNode results = root.get("results");

            if (results != null && results.isArray()) {
                for (JsonNode station : results) {
                    stations.add(objectMapper.treeToValue(station, BloodStationDto.class));
                }
            }

            stations = stations.stream()
                    .filter(s -> s.getClosed() == null || !s.getClosed())
                    .toList();

            return stations;

        } catch (Exception e) {
            throw new DonorSearchUnavailableException("DonorSearch API failed", e);
        }
    }
}