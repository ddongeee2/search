package io.github.ddongeee.search.http.client;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class HttpClient {
    private final RestTemplate restTemplate;

    public record HttpClientRequest(
            String uri,
            HttpMethod method,
            HttpHeaders headers
    ){
        @Builder
        public HttpClientRequest {}
    }

    public String request(final HttpClientRequest request) {
        return restTemplate.exchange(
                request.uri(),
                request.method(),
                new HttpEntity<>(request.headers()),
                new ParameterizedTypeReference<String>() {}
        ).getBody();
    }
}
