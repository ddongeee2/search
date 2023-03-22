package io.github.ddongeee.search.port.input.statistics;

import io.github.ddongeee.search.port.input.statistics.response.BlogStatisticsDto;
import lombok.Builder;

import java.util.List;

public interface SearchBlogStatisticsUseCase {
    List<BlogStatisticsDto> search(final SearchBlogStatisticsCommand command);

    record SearchBlogStatisticsCommand(Long top) {
        @Builder
        public SearchBlogStatisticsCommand {}
    }
}
