package io.github.ddongeee.search.port.output.blog;

import io.github.ddongeee.search.contract.RestApiType;
import io.github.ddongeee.search.contract.blog.BlogSearchQuerySort;
import io.github.ddongeee.search.domain.document.Blog;
import lombok.Builder;

public interface BlogPort {
    Blog searchBlogDocuments(SearchClause clause);

    record SearchClause(
            String keyword,
            String url,
            BlogSearchQuerySort sort,
            RestApiType restApiType,
            int page,
            int size
    ) {
        @Builder
        public SearchClause {}
    }
}
