package io.github.ddongeee.search.port.output.blog.clause;

import lombok.Builder;
import lombok.Getter;
import io.github.ddongeee.search.contract.RestApiType;
import io.github.ddongeee.search.contract.blog.BlogSearchQuerySort;
import io.github.ddongeee.search.port.output.Clause;

import static io.github.ddongeee.search.port.output.blog.BlogPort.*;

@Getter
public class NaverBlogSearchClause extends Clause {
    private final String keyword;
    private final BlogSearchQuerySort sort;

    private final int start;
    private final int display;

    @Builder
    public NaverBlogSearchClause(String keyword, BlogSearchQuerySort sort,
                                 int start, int display) {
        super(RestApiType.NAVER_SEARCH_BLOGS);
        this.keyword = keyword;
        this.sort = sort;
        this.start = start;
        this.display = display;
    }

    public static NaverBlogSearchClause of(SearchClause clause) {
        return NaverBlogSearchClause.builder()
                .keyword(clause.keyword())
                .sort(clause.sort())
                .start(clause.page())
                .display(clause.size())
                .build();
    }
}
