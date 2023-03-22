package io.github.ddongeee.search.http.blog;

import static io.github.ddongeee.search.http.client.HttpClient.HttpClientRequest;
import static io.github.ddongeee.search.port.output.blog.BlogPort.SearchClause;

public interface RestTemplateBuilder {
//    String buildUri(Clause clause);
//    HttpHeaders buildHeaders();
    HttpClientRequest buildRestRequest(SearchClause clause);
}
