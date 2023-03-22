package io.github.ddongeee.search.http.blog.kakao;

import io.github.ddongeee.search.contract.RestApiType;
import io.github.ddongeee.search.domain.document.Blog;
import io.github.ddongeee.search.domain.document.BlogDocument;
import io.github.ddongeee.search.http.blog.BlogHttpClient;
import io.github.ddongeee.search.http.blog.RestTemplateBuilder;
import io.github.ddongeee.search.http.client.HttpClient;
import io.github.ddongeee.search.http.util.ObjectMapperUtil;
import io.github.ddongeee.search.port.input.Pagination;
import io.github.ddongeee.search.port.output.blog.clause.KakaoBlogSearchClause;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import static io.github.ddongeee.search.port.output.blog.BlogPort.*;

@Component
@RequiredArgsConstructor
public class KakaoBlogHttpClient implements BlogHttpClient {
    private final RestTemplateBuilder kakaoBlogSearchBuilder;
    private final HttpClient httpClient;
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    public RestApiType restApiType() {
        return RestApiType.KAKAO_SEARCH_BLOGS;
    }

    @Override
    public Blog search(SearchClause clause) {
        var request = kakaoBlogSearchBuilder.buildRestRequest(clause);
        var response = httpClient.request(request);
        KakaoBlogResponse kakaoBlogResponse = objectMapperUtil.readValue(response, KakaoBlogResponse.class);
        return convert(kakaoBlogResponse, clause);
    }

    public Blog convert(final KakaoBlogResponse source, final SearchClause searchClause) {
        final var clause = KakaoBlogSearchClause.of(searchClause);
        final var documents = source.documents();
        final var blogDocuments = documents.stream()
                                            .map(each -> BlogDocument.builder()
                                                    .title(each.title())
                                                    .contents(each.contents())
                                                    .blogName(each.blogname())
                                                    .url(each.url())
                                                    .thumbnail(each.thumbnail())
                                                    .writtenAt(each.datetime())
                                                    .build())
                                            .toList();

        final var page = clause.getPage();
        final var size = clause.getSize();
        final var meta = source.meta();
        final var pagination = new Pagination(page, meta.getPageableCount(), meta.getTotalCount(), size);

        return Blog.builder()
                .blogDocuments(blogDocuments)
                .pagination(pagination)
                .build();
    }
}
