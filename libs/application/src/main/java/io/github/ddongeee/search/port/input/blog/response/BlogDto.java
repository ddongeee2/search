package io.github.ddongeee.search.port.input.blog.response;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import io.github.ddongeee.search.port.input.Pagination;

import java.util.List;

@Getter
public record BlogDto(
        List<BlogDocumentDto> documents,
        Pagination pagination
) {
    public static BlogDto empty() {
        return BlogDto.builder()
                .documents(Lists.newArrayList())
                .pagination(Pagination.empty())
                .build();
    }
    @Builder
    public BlogDto {}
}
