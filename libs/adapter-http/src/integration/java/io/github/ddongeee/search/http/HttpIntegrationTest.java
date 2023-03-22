package io.github.ddongeee.search.http;

import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@RestClientTest(includeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "io\\.github\\.ddongeee\\.search\\.http\\..*")})
public abstract class HttpIntegrationTest {

}
