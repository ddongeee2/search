package io.github.ddongeee.search.port.output.statistics;

import io.github.ddongeee.search.domain.statistics.BlogStatistic;

import java.util.List;
import java.util.Optional;

public interface BlogStatisticsPort {
    void save(BlogStatistic blogStatistic);

    Optional<BlogStatistic> findByKeyword(String keyword);

    List<BlogStatistic> findTopNByOrderByCountDesc(Long top);
}
