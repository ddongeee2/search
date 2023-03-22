package io.github.ddongeee.search.persistence.statistics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.ddongeee.search.IntegrationTest;
import io.github.ddongeee.search.domain.statistics.BlogStatistic;
import io.github.ddongeee.search.domain.statistics.CreateBlogStatistic;

import static org.assertj.core.api.Assertions.assertThat;

class BlogStatisticSaveJpaRepositoryTest extends IntegrationTest {
    @Autowired
    private BlogStatisticJpaRepository blogStatisticJpaRepository;

    @Test
    @DisplayName("블로그 통계를 저장할 수 있다.")
    void save() {
        var blogStat = BlogStatistic.create(
                CreateBlogStatistic.builder()
                        .keyword("keyword")
                        .build()
        );

        blogStatisticJpaRepository.save(blogStat);

        assertThat(blogStat).isNotNull();
        assertThat(blogStat.getId()).isNotNull();
        assertThat(blogStat.getCount()).isEqualTo(1L);
        assertThat(blogStat.getKeyword()).isEqualTo("keyword");
    }
}
