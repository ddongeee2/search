package io.github.ddongeee.search.persistence.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.ddongeee.search.domain.statistics.BlogStatistic;

public interface BlogStatisticJpaRepository extends JpaRepository<BlogStatistic, Long>, BlogStatisticCustomRepository {

}
