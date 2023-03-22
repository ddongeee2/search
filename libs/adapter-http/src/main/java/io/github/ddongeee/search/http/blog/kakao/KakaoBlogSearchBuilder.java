package io.github.ddongeee.search.http.blog.kakao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.github.ddongeee.search.contract.blog.BlogSearchQuerySort;
import io.github.ddongeee.search.http.blog.RestTemplateBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

import static io.github.ddongeee.search.http.client.HttpClient.*;
import static io.github.ddongeee.search.port.output.blog.BlogPort.*;

/**
 * 참고 : https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog
 */
@Component
public class KakaoBlogSearchBuilder implements RestTemplateBuilder {
    private final String baseUrl;
    private final String restApiKey;
    private static final String QUERY = "query";
    private static final String SORT = "sort";
    private static final String PAGE = "page";
    private static final String SIZE = "size";
    private static final String RECENCY = "recency";
    private static final String ACCURACY = "accuracy";

    private static final String AUTHORIZATION = "Authorization";

    public KakaoBlogSearchBuilder(
            @Value("${kakao-open-api.base-url}") String baseUrl,
            @Value("${kakao-open-api.auth.rest-api-key}") String restApiKey
    ) {
        this.baseUrl = baseUrl;
        this.restApiKey = restApiKey;
    }

    private String buildQuery(SearchClause clause) {
        if (StringUtils.isNotBlank(clause.url())) {
            return String.join(
                    StringUtils.SPACE,
                    Lists.newArrayList(clause.url(), clause.keyword())
            );
        }

        return clause.keyword();
    }

    private String buildSort(SearchClause clause) {
        if (clause.sort() == BlogSearchQuerySort.RECENCY) {
            return RECENCY;
        }

        return ACCURACY;
    }

    public String buildUri(SearchClause searchClause) {
        var restApiType = searchClause.restApiType();

        return UriComponentsBuilder.fromUriString(baseUrl)
                .path(restApiType.getUri())
                .queryParam(QUERY, buildQuery(searchClause))
                .queryParam(SORT, buildSort(searchClause))
                .queryParam(PAGE, searchClause.page())
                .queryParam(SIZE, searchClause.size())
                .build()
                .toUriString();
    }

    public HttpHeaders buildHeaders() {
        Map<String, String> headers = Maps.newHashMap();
        headers.put(AUTHORIZATION, "KakaoAK " + restApiKey);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAll(headers);
        return httpHeaders;
    }

    @Override
    public HttpClientRequest buildRestRequest(SearchClause clause) {
        return HttpClientRequest.builder()
                .uri(buildUri(clause))
                .method(clause.restApiType().getHttpMethod())
                .headers(buildHeaders())
                .build();
    }
}
