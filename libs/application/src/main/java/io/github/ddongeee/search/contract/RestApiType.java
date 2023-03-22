package io.github.ddongeee.search.contract;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

@Getter
@RequiredArgsConstructor
public enum RestApiType {
    KAKAO_SEARCH_BLOGS("/v2/search/blog", HttpMethod.GET, ServiceProvider.KAKAO),
    NAVER_SEARCH_BLOGS("/v1/search/blog", HttpMethod.GET, ServiceProvider.NAVER);
    private final String uri;
    private final HttpMethod httpMethod;
    private final ServiceProvider serviceProvider;
}
