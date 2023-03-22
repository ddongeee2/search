package io.github.ddongeee.search.controller.statistics;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import io.github.ddongeee.search.port.input.statistics.SearchBlogStatisticsUseCase;
import io.github.ddongeee.search.port.input.statistics.SearchBlogStatisticsUseCase.SearchBlogStatisticsCommand;
import io.github.ddongeee.search.port.input.statistics.response.BlogStatisticsDto;
import io.github.ddongeee.search.controller.ResultResponse;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.beans.ConstructorProperties;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class BlogStatisticsSearchController {
    private final SearchBlogStatisticsUseCase searchBlogStatisticsUseCase;

    @Getter
    static class SearchBlogStatisticsRequestBody {
        @Min(1)
        @Max(10)
        private Long top = 10L;

        @ConstructorProperties({"top"})
        public SearchBlogStatisticsRequestBody(final Long top) {
            if (Objects.nonNull(top)) {
                this.top = top;
            }
        }
    }

    @GetMapping("/api/v1/blogs/statistics/popular") // todo : rename
    public ResultResponse<List<BlogStatisticsDto>> searchPopularSearchKeyword(
            @Valid final SearchBlogStatisticsRequestBody requestBody
    ) {
        var command = SearchBlogStatisticsCommand.builder()
                .top(requestBody.getTop())
                .build();
        return ResultResponse.ok(searchBlogStatisticsUseCase.search(command));
    }


}
