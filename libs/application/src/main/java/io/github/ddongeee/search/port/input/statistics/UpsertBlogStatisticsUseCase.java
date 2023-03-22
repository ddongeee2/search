package io.github.ddongeee.search.port.input.statistics;

import lombok.Builder;

public interface UpsertBlogStatisticsUseCase {
    void increaseCountOrCreate(final UpsertBlogStatisticsCommand command);
    
    record UpsertBlogStatisticsCommand(
            String keyword
    ) {
        public static UpsertBlogStatisticsCommand of(final String keyword) {
            return new UpsertBlogStatisticsCommand(keyword);
        }
    }
}
