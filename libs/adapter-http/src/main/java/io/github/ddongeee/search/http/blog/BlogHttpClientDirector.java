package io.github.ddongeee.search.http.blog;

import io.github.ddongeee.search.contract.RestApiType;
import io.github.ddongeee.search.domain.document.Blog;
import io.github.ddongeee.search.exception.InvalidRestApiRequestException;
import io.github.ddongeee.search.port.output.blog.BlogPort;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class BlogHttpClientDirector implements BlogPort {
    private final Map<RestApiType, BlogHttpClient> blogHttpClientMap;

    public BlogHttpClientDirector(List<BlogHttpClient> blogHttpClients) {
        this.blogHttpClientMap = new HashMap<>();

        for (BlogHttpClient client : blogHttpClients) {
            if (blogHttpClientMap.get(client.restApiType()) != null) {
                throw new RuntimeException("Client's key is not unique : " + client.restApiType());
            }
            blogHttpClientMap.put(client.restApiType(), client);
        }
    }

    @Override
    @CircuitBreaker(name = "BlogHttpClient", fallbackMethod = "searchByNaver")
    public Blog searchBlogDocuments(final SearchClause clause) {
        BlogHttpClient blogHttpClient = blogHttpClientMap.get(clause.restApiType());
        if (blogHttpClient == null) {
            throw new InvalidRestApiRequestException(clause.restApiType());
        }
        return blogHttpClient.search(clause);
    }

    public Blog searchByNaver(final SearchClause clause, final Throwable e) {
        log.warn("[CIRCUIT-OPENED] SEARCH (Kakao -> Naver). errMsg: {}", e.getMessage(), e);
        BlogHttpClient naverBlogHttpClient = blogHttpClientMap.get(RestApiType.NAVER_SEARCH_BLOGS);
        return naverBlogHttpClient.search(clause);
    }
}
