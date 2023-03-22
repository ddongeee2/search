package io.github.ddongeee.search.persistence.statistics;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import io.github.ddongeee.search.IntegrationTest;

import static org.assertj.core.api.Assertions.assertThat;

class BlogStatisticSearchJpaRepositoryTest extends IntegrationTest {
    @Autowired
    private BlogStatisticJpaRepository blogStatisticJpaRepository;

    @Test
    @DisplayName("블로그 검색어로 조회할 수 있다.")
    void findByKeyword() {
        var result = blogStatisticJpaRepository.findByKeyword("자바");

        Assertions.assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(10L);
        assertThat(result.get().getKeyword()).isEqualTo("자바");
        assertThat(result.get().getCount()).isEqualTo(4232L);
    }

    @Test
    @DisplayName("상위 N 개의 검색어를 조회할 수 있다.")
    void findTopNByOrderByCountDesc() {
        var result = blogStatisticJpaRepository.findTopNByOrderByCountDesc(3L);

        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result).element(0)
                .hasFieldOrPropertyWithValue("id", 14L)
                .hasFieldOrPropertyWithValue("keyword", "테스트환경")
                .hasFieldOrPropertyWithValue("count", 49237L)
        ;
        Assertions.assertThat(result).element(1)
                .hasFieldOrPropertyWithValue("id", 12L)
                .hasFieldOrPropertyWithValue("keyword", "코틀린")
                .hasFieldOrPropertyWithValue("count", 40104L)
        ;
        Assertions.assertThat(result).element(2)
                .hasFieldOrPropertyWithValue("id", 13L)
                .hasFieldOrPropertyWithValue("keyword", "JPA")
                .hasFieldOrPropertyWithValue("count", 5783L)
        ;
    }
}
