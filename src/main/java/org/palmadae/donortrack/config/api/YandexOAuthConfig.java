package org.palmadae.donortrack.config.api;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "yandex")
@NoArgsConstructor
public class YandexOAuthConfig {
    private String clientId;
    private String clientSecret;
}
