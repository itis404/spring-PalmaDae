    package org.palmadae.donortrack.config.api;

    import lombok.Data;
    import lombok.NoArgsConstructor;
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.stereotype.Component;

    @Data
    @Component
    @ConfigurationProperties(prefix = "donorsearch")
    public class DonorSearchConfig {

        private Api api;

        @Data
        public static class Api {
            private String baseUrl;
            private EndPoints endPoints;
        }

        @Data
        public static class EndPoints {
            private String stations;
        }
    }