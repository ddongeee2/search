package io.github.ddongeee.search.port.input.blog;

import io.github.ddongeee.search.contract.RestApiType;
import io.github.ddongeee.search.contract.blog.BlogSearchQuerySort;
import io.github.ddongeee.search.port.input.blog.response.BlogDto;
import lombok.Builder;

public interface SearchBlogUseCase {
    BlogDto search(final SearchCommand command);

    record SearchCommand(
            String keyword,
            RestApiType restApiType,
            BlogSearchQuerySort sort,
            int page,
            int size
    ) {
        @Builder
        public SearchCommand {}
    }
}
