package org.palmadae.donortrack.service.user;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@Service
public class DadataService {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String apiKey = "ec00a2192ae81fa6ae42043b223c7ede21e932ea";
    private final String secret = "0748dd0ccdbddb2657542fabf1dce88554ff9bc";

    public List<String> suggestCities(String query) {

        String url = "https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Token " + apiKey);
        headers.set("X-Secret", secret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
        {
          "query": "%s",
          "from_bound": { "value": "city" },
          "to_bound": { "value": "city" }
        }
        """.formatted(query);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                JsonNode.class
        );

        List<String> result = new ArrayList<>();

        JsonNode bodyNode = response.getBody();

        if (bodyNode != null && bodyNode.has("suggestions")) {
            bodyNode.get("suggestions").forEach(s -> {
                JsonNode data = s.get("data");
                if (data != null && data.has("city") && !data.get("city").isNull()) {
                    result.add(data.get("city").asText());
                } else {
                    result.add(s.get("value").asText().replace("г ", ""));
                }
            });
        }

        return result;
    }
}