package io.github.ddongeee.search.http.blog;

import io.github.ddongeee.search.contract.RestApiType;
import io.github.ddongeee.search.domain.document.Blog;

import static io.github.ddongeee.search.port.output.blog.BlogPort.*;

public interface BlogHttpClient {
    RestApiType restApiType();
    Blog search(SearchClause clause);
}
