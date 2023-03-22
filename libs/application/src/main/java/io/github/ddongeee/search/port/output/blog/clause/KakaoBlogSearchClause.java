package io.github.ddongeee.search.port.output.blog.clause;

import lombok.Builder;
import lombok.Getter;
import io.github.ddongeee.search.contract.RestApiType;
import io.github.ddongeee.search.contract.blog.BlogSearchQuerySort;
import io.github.ddongeee.search.port.output.Clause;
import io.github.ddongeee.search.port.output.blog.BlogPort.SearchClause;

@Getter
public class KakaoBlogSearchClause extends Clause {
    private final String keyword;
    private final String url;
    private final BlogSearchQuerySort sort;
    private final int page;
    private final int size;

    @Builder
    public KakaoBlogSearchClause(String keyword, String url, BlogSearchQuerySort sort,
                                 int page, int size) {
        super(RestApiType.KAKAO_SEARCH_BLOGS);

        this.keyword = keyword;
        this.url = url;
        this.sort = sort;
        this.page = page;
        this.size = size;
    }

    public static KakaoBlogSearchClause of(SearchClause clause) {
        return KakaoBlogSearchClause.builder()
                .keyword(clause.keyword())
                .url(clause.url())
                .sort(clause.sort())
                .size(clause.size())
                .page(clause.page())
                .build();
    }
}
