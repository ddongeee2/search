package io.github.ddongeee.search.controller.blog;

import io.github.ddongeee.search.contract.RestApiType;
import io.github.ddongeee.search.contract.blog.BlogSearchQuerySort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.ddongeee.search.port.input.blog.SearchBlogUseCase;
import io.github.ddongeee.search.port.input.blog.SearchBlogUseCase.SearchCommand;
import io.github.ddongeee.search.port.input.blog.response.BlogDto;
import io.github.ddongeee.search.controller.ResultResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.beans.ConstructorProperties;

@RestController
@RequiredArgsConstructor
public class BlogSearchController {
    private final SearchBlogUseCase searchBlogUseCase;

    record SearchBlogRequestParams(
            @NotBlank(message = "키워드를 입력하세요.")
            String keyword,
            RestApiType restApiType,
            BlogSearchQuerySort sort,
            Integer page,
            Integer size
    ) {
        @Override
        public RestApiType restApiType() {
            return this.restApiType == null ? RestApiType.KAKAO_SEARCH_BLOGS : this.restApiType;
        }

        @Override
        public Integer page() {
            return this.page == null ? 1 : this.page;
        }

        @Override
        public Integer size() {
            return this.size == null ? 10 : this.size;
        }

        @ConstructorProperties({"keyword", "restApiType", "sort", "page", "size"})
        public SearchBlogRequestParams {}
    }

    @GetMapping("/api/v1/search/blog")
    public ResultResponse<BlogDto> searchBlog(@Valid final SearchBlogRequestParams requestBody) {
        final var command = SearchCommand.builder()
                .keyword(requestBody.keyword())
                .restApiType(requestBody.restApiType())
                .sort(requestBody.sort())
                .page(requestBody.page())
                .size(requestBody.size())
                .build();

        final var blogDto = searchBlogUseCase.search(command);

        return ResultResponse.ok(blogDto);
    }
}
