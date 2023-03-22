package io.github.ddongeee.search.http.blog.naver;

import io.github.ddongeee.search.contract.RestApiType;
import io.github.ddongeee.search.domain.document.Blog;
import io.github.ddongeee.search.domain.document.BlogDocument;
import io.github.ddongeee.search.http.blog.BlogHttpClient;
import io.github.ddongeee.search.http.blog.RestTemplateBuilder;
import io.github.ddongeee.search.http.client.HttpClient;
import io.github.ddongeee.search.http.util.ObjectMapperUtil;
import io.github.ddongeee.search.port.input.Pagination;
import io.github.ddongeee.search.port.output.blog.clause.NaverBlogSearchClause;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static io.github.ddongeee.search.port.output.blog.BlogPort.SearchClause;

@Component
@RequiredArgsConstructor
public class NaverBlogHttpClient implements BlogHttpClient {
    private final RestTemplateBuilder naverBlogSearchBuilder;
    private final HttpClient httpClient;
    private final ObjectMapperUtil objectMapperUtil;

    @Override
    public RestApiType restApiType() {
        return RestApiType.NAVER_SEARCH_BLOGS;
    }

    @Override
    public Blog search(SearchClause clause) {
        var request = naverBlogSearchBuilder.buildRestRequest(clause);
        var response = httpClient.request(request);
        NaverBlogResponse naverBlogResponse = objectMapperUtil.readValue(response, NaverBlogResponse.class);
        return convert(naverBlogResponse, clause);
    }

    private Blog convert(final NaverBlogResponse source, final SearchClause searchClause) {
        final var clause = NaverBlogSearchClause.of(searchClause);
        final var documents = source.getItems();
        final var blogDocuments = documents.stream()
                .map(each -> BlogDocument.builder()
                        .title(each.getTitle())
                        .contents(each.getDescription())
                        .blogName(each.getBloggername())
                        .url(each.getLink())
                        .thumbnail(Strings.EMPTY)
                        .writtenAt(
                                ZonedDateTime.of(
                                        LocalDate.parse(each.getPostdate(), DateTimeFormatter.BASIC_ISO_DATE),
                                        LocalTime.MIDNIGHT,
                                        ZoneId.systemDefault())
                        )
                        .build())
                .toList();

        final var page = clause.getStart();
        final var size = clause.getDisplay();
        final var pagination = new Pagination(page, (int) (source.getTotal() / size) + 1, source.getTotal(), size);

        return Blog.builder()
                .blogDocuments(blogDocuments)
                .pagination(pagination)
                .build();
    }
}
