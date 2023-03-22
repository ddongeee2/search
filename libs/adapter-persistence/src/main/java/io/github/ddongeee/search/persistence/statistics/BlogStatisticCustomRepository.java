package io.github.ddongeee.search.persistence.statistics;

import io.github.ddongeee.search.domain.statistics.BlogStatistic;

import java.util.List;
import java.util.Optional;

public interface BlogStatisticCustomRepository {
    Optional<BlogStatistic> findByKeyword(String keyword);
    List<BlogStatistic> findTopNByOrderByCountDesc(Long top);
}
