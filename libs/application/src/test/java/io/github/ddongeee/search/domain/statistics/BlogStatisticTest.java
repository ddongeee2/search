package io.github.ddongeee.search.domain.statistics;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.generator.BuilderArbitraryGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BlogStatisticTest {
    @DisplayName("블로그 통계 엔티티를 생성한다.")
    @Test
    void create() {
        var fixtureMonkey = FixtureMonkey.builder()
                .putGenerator(CreateBlogStatistic.class, BuilderArbitraryGenerator.INSTANCE)
                .build();

        var create = fixtureMonkey.giveMeOne(CreateBlogStatistic.class);

        var actual = BlogStatistic.create(create);

        assertThat(actual)
                .hasFieldOrPropertyWithValue("keyword", create.getKeyword())
                .hasFieldOrPropertyWithValue("count", 1L)
        ;
    }

    @DisplayName("조회수를 증가한다")
    @Test
    void increaseCount() {
        var fixtureMonkey = FixtureMonkey.builder()
                .putGenerator(CreateBlogStatistic.class, BuilderArbitraryGenerator.INSTANCE)
                .build();

        var create = fixtureMonkey.giveMeOne(CreateBlogStatistic.class);

        var actual = BlogStatistic.create(create);
        actual.increaseCount();

        assertThat(actual)
                .hasFieldOrPropertyWithValue("keyword", create.getKeyword())
                .hasFieldOrPropertyWithValue("count", 2L)
        ;
    }

}
