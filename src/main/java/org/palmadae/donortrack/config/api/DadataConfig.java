package org.palmadae.donortrack.config.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "dadata")
@NoArgsConstructor
public class DadataConfig {
    private String apiToken;
    private String secretKey;
}
