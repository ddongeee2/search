package io.github.ddongeee.search.domain.document;

import lombok.Builder;
import lombok.Getter;
import io.github.ddongeee.search.port.input.Pagination;

import java.util.List;

@Getter
public class Blog {
    private final List<BlogDocument> blogDocuments;
    private final Pagination pagination;

    @Builder
    public Blog(List<BlogDocument> blogDocuments, Pagination pagination) {
        this.blogDocuments = blogDocuments;
        this.pagination = pagination;
    }
}
