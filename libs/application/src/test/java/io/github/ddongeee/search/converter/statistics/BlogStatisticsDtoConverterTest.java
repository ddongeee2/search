package io.github.ddongeee.search.converter.statistics;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.generator.BuilderArbitraryGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import io.github.ddongeee.search.domain.statistics.BlogStatistic;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BlogStatisticsDtoConverterTest {
    @InjectMocks
    private BlogStatisticsDtoConverterImpl sut;

    @BeforeEach
    void setup() {
        sut = new BlogStatisticsDtoConverterImpl();
    }

    @Test
    @DisplayName("BlogStatistic 을 BlogStatisticDto 로 변환할 수 있다.")
    void convert() {
        var fixtureMonkey = FixtureMonkey.builder()
                .putGenerator(BlogStatistic.class, BuilderArbitraryGenerator.INSTANCE)
                .build();

        var blogStat = fixtureMonkey.giveMeOne(BlogStatistic.class);

        var blogStatDto = sut.convert(blogStat);

        assertThat(blogStatDto).isNotNull();
        assertThat(blogStatDto.getCount()).isEqualTo(blogStat.getCount());
        assertThat(blogStatDto.getKeyword()).isEqualTo(blogStat.getKeyword());
    }
}
