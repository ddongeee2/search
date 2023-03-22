package io.github.ddongeee.search.http.blog.naver;

import com.google.common.collect.Maps;
import io.github.ddongeee.search.contract.RestApiType;
import io.github.ddongeee.search.contract.blog.BlogSearchQuerySort;
import io.github.ddongeee.search.http.blog.RestTemplateBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import io.github.ddongeee.search.port.output.Clause;
import io.github.ddongeee.search.port.output.blog.clause.NaverBlogSearchClause;

import java.util.Map;

import static io.github.ddongeee.search.http.client.HttpClient.*;
import static io.github.ddongeee.search.port.output.blog.BlogPort.*;

/**
 * 참고 : https://developers.naver.com/docs/serviceapi/search/blog/blog.md
 */
@Component
public class NaverBlogSearchBuilder implements RestTemplateBuilder {
    private final String baseUrl;
    private final String clientId;
    private final String clientSecret;

    private static final String QUERY = "query";
    private static final String SORT = "sort";
    private static final String PAGE = "start";
    private static final String SIZE = "display";

    private static final String RECENCY = "recency"; // date
    private static final String ACCURACY = "sim"; // 정확도순

    private static final String X_NAVER_CLIENT_ID = "X-Naver-Client-Id";
    private static final String X_NAVER_CLIENT_SECRET = "X-Naver-Client-Secret";

    public NaverBlogSearchBuilder(
            @Value("${naver-open-api.base-url}") String baseUrl,
            @Value("${naver-open-api.auth.client-id}") String clientId,
            @Value("${naver-open-api.auth.client-secret}") String clientSecret
    ) {
        this.baseUrl = baseUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    private String buildSort(NaverBlogSearchClause clause) {
        if (clause.getSort() == BlogSearchQuerySort.RECENCY) {
            return RECENCY;
        }

        return ACCURACY;
    }

    public String buildUri(Clause clause) {
        var searchClause = (NaverBlogSearchClause) clause;
        var restApiType = searchClause.getRestApiType();

        return UriComponentsBuilder.fromUriString(baseUrl)
                .path(restApiType.getUri())
                .queryParam(QUERY, searchClause.getKeyword())
                .queryParam(SORT, buildSort(searchClause))
                .queryParam(PAGE, searchClause.getStart())
                .queryParam(SIZE, searchClause.getDisplay())
                .build()
                .toUriString();
    }

    public HttpHeaders buildHeaders() {
        Map<String, String> headers = Maps.newHashMap();
        headers.put(X_NAVER_CLIENT_ID, clientId);
        headers.put(X_NAVER_CLIENT_SECRET, clientSecret);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return httpHeaders;
    }

    public boolean isTarget(RestApiType type) {
        return type == RestApiType.NAVER_SEARCH_BLOGS;
    }

    @Override
    public HttpClientRequest buildRestRequest(SearchClause clause) {
        return null;
    }
}
