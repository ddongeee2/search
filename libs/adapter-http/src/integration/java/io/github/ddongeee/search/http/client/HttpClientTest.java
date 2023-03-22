package io.github.ddongeee.search.http.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import io.github.ddongeee.search.http.HttpIntegrationTest;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class HttpClientTest extends HttpIntegrationTest {
    @Autowired
    private HttpClient sut;

    @Autowired
    private MockRestServiceServer mockServer;

    @Test
    void test() {
        String expectedUrl = "http://localhost:8080/api/v1/sample";
        String expectResult = "result";
        var httpMethod = HttpMethod.GET;
        var headers = new HttpHeaders();

        mockServer.expect(requestTo(expectedUrl))
                .andExpect(method(httpMethod))
                .andRespond(withSuccess(expectResult, MediaType.APPLICATION_JSON));

        var clientRequest = HttpClient.HttpClientRequest.builder()
                        .uri("http://localhost:8080/api/v1/sample")
                        .method(HttpMethod.GET)
                        .headers(headers)
                        .build();

        sut.request(clientRequest);
    }
}
