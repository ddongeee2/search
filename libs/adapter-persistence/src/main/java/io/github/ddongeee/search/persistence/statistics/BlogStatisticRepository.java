package io.github.ddongeee.search.persistence.statistics;

import org.springframework.stereotype.Component;
import io.github.ddongeee.search.domain.statistics.BlogStatistic;
import io.github.ddongeee.search.port.output.statistics.BlogStatisticsPort;

import java.util.List;
import java.util.Optional;

@Component
public class BlogStatisticRepository implements BlogStatisticsPort {

    private final BlogStatisticJpaRepository blogStatisticJpaRepository;

    public BlogStatisticRepository(BlogStatisticJpaRepository blogStatisticJpaRepository) {
        this.blogStatisticJpaRepository = blogStatisticJpaRepository;
    }

    @Override
    public void save(BlogStatistic blogStatistic) {
        blogStatisticJpaRepository.save(blogStatistic);
    }

    @Override
    public Optional<BlogStatistic> findByKeyword(String keyword) {
        return blogStatisticJpaRepository.findByKeyword(keyword);
    }

    @Override
    public List<BlogStatistic> findTopNByOrderByCountDesc(Long top) {
        return blogStatisticJpaRepository.findTopNByOrderByCountDesc(top);
    }
}
