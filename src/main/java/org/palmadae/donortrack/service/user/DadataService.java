package org.palmadae.donortrack.service.user;

import jakarta.annotation.PostConstruct;
import org.palmadae.donortrack.config.api.DadataConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DadataService {

    @Autowired
    private DadataConfig dadataConfig;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private RestTemplate restTemplate;
    private String apiKey;
    private String secret;

    @PostConstruct
    public void init() {
        this.restTemplate = new RestTemplate();
        this.apiKey = dadataConfig.getApiToken();
        this.secret = dadataConfig.getSecretKey();
    }

    @SuppressWarnings("unchecked")
    public List<String> suggestCities(String query) {
        String cacheKey = "city:" + query.toLowerCase();

        List<String> cached = (List<String>) redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            System.out.println("Взято из Redis: " + query);
            return cached;
        }

        System.out.println("Запрос к API DaData: " + query);

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


        if (!result.isEmpty()) {
            redisTemplate.opsForValue().set(cacheKey, result, 24, TimeUnit.HOURS);
        }

        return result;
    }
}