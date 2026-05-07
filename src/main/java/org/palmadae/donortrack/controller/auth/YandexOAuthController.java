package org.palmadae.donortrack.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.palmadae.donortrack.config.api.YandexOAuthConfig;
import org.palmadae.donortrack.dto.YandexUserDto;
import org.palmadae.donortrack.entity.UserEntity;
import org.palmadae.donortrack.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.Authenticator;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@RestController
public class YandexOAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private YandexOAuthConfig config;

    private String clientId;
    private String clientSecret;

    @PostConstruct
    public void init() {
        clientId = config.getClientId();
        clientSecret = config.getClientSecret();
    }

    @GetMapping("/oauth/yandex")
    public void redirectToYandex(HttpServletResponse response)
            throws IOException {

        String redirectUri =
                URLEncoder.encode(
                        "http://localhost:8080/oauth/yandex/callback",
                        StandardCharsets.UTF_8
                );

        String url =
                "https://oauth.yandex.ru/authorize" +
                        "?response_type=code" +
                        "&client_id=" + clientId +
                        "&redirect_uri=" + redirectUri;

        response.sendRedirect(url);
    }

    @GetMapping("/oauth/yandex/callback")
    public void callback(@RequestParam String code,
                         HttpServletResponse response,
                         HttpServletRequest request) throws IOException {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> tokenRequest =
                new HttpEntity<>(body, headers);

        ResponseEntity<Map> tokenResponse =
                restTemplate.postForEntity(
                        "https://oauth.yandex.ru/token",
                        tokenRequest,
                        Map.class
                );

        String accessToken = tokenResponse.getBody()
                .get("access_token")
                .toString();

        HttpHeaders userHeaders = new HttpHeaders();
        userHeaders.setBearerAuth(accessToken);

        HttpEntity<Void> userRequest = new HttpEntity<>(userHeaders);

        ResponseEntity<Map> userResponse =
                restTemplate.exchange(
                        "https://login.yandex.ru/info",
                        HttpMethod.GET,
                        userRequest,
                        Map.class
                );

        ObjectMapper mapper = new ObjectMapper();

        YandexUserDto dto = mapper.convertValue(
                userResponse.getBody(),
                YandexUserDto.class
        );

        UserEntity user = userService.createUserYandex(dto);

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority(user.getRole().name()))
                );

        System.out.println("AUTH CLASS = " + auth.getClass());
        System.out.println("PRINCIPAL CLASS = " + auth.getPrincipal().getClass());
        System.out.println("NAME = " + auth.getName());

        SecurityContextHolder.getContext().setAuthentication(auth);

        request.getSession(true).setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        response.sendRedirect("/home");
    }
}